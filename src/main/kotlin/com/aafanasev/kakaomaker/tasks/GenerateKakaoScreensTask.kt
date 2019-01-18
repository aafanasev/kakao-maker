package com.aafanasev.kakaomaker.tasks

import com.aafanasev.kakaomaker.KakaoMakerPlugin
import com.aafanasev.kakaomaker.KakaoMakerPluginExtension
import com.aafanasev.kakaomaker.util.*
import com.android.build.gradle.BaseExtension
import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.file.FileCollection
import org.gradle.api.tasks.TaskAction
import org.gradle.kotlin.dsl.get
import org.w3c.dom.Element
import java.io.File
import javax.annotation.Generated
import javax.xml.parsers.DocumentBuilder
import javax.xml.parsers.DocumentBuilderFactory

open class GenerateKakaoScreensTask : DefaultTask() {

    private val androidExtension = project.extensions["android"] as BaseExtension
    private val kakaoMakerExtension = project.extensions[KakaoMakerPlugin.EXTENSION_NAME] as KakaoMakerPluginExtension

    private val generatedAnnotation by lazy(LazyThreadSafetyMode.NONE) {
        AnnotationSpec.builder(Generated::class.java)
                .addMember("value = [%S]", "kakao-maker")
                .addMember("comments = %S", "https://github.com/aafanasev/kakao-maker")
                .build()
    }

    @TaskAction
    fun action() {
        ensureOutputDir()

        val layouts = getLayoutFiles()
        val xmlParser = getXmlParser()

        layouts.forEach {
            val document = xmlParser.parse(it)

            if (document.documentElement.isMergeTag.not()) {
                val screenName = document.documentElement.kakaoScreenName

                if (screenName.isNotEmpty()) {
                    log("Generating $screenName...")

                    val classBuilder = TypeSpec.classBuilder(screenName)

                    // classBuilder.addAnnotation(generatedAnnotation)

                    traverse(classBuilder, document.documentElement)

                    val file = FileSpec.builder(kakaoMakerExtension.packageName!!, "$screenName.kt")
                            .addType(classBuilder.build())
                            .addImport(kakaoMakerExtension.applicationId!!, "R")
                            .build()

                    // file.writeTo(System.out)

                    File(kakaoMakerExtension.outputDir, file.name).writeText(file.toString())
                }
            }

            xmlParser.reset()
        }
    }

    private fun traverse(classBuilder: TypeSpec.Builder, element: Element) {
        if (element.isMergeTag || element.isIncludeTag) {
            // TODO: support merge and include tags later
            return
        }

        val id = element.id

        if (id.isNotEmpty()) {
            val name = Helper.viewIdToName(id)
            val type = KakaoTypeProvider.getType(element.tagName)

            val property = PropertySpec.builder(name, type)
                    .initializer("%L { withId(R.id.%L) } ", type.simpleName, id)
                    .build()

            classBuilder.addProperty(property)
        }

        if (element.hasChildNodes()) {
            for (index in 0 until element.childNodes.length) {
                val node = element.childNodes.item(index)

                if (node.nodeType == Element.ELEMENT_NODE) {
                    traverse(classBuilder, node as Element)
                }
            }
        }
    }

    private fun ensureOutputDir() {
        val outputDir: File = kakaoMakerExtension.outputDir ?: throw GradleException("Output path is not specified")

        when {
            outputDir.exists() -> log("Output directory already exists")
            outputDir.mkdirs() -> log("Output directory successfully created")
            else -> throw GradleException("Cannot create output directory: ${outputDir.path}")
        }
    }

    private fun getLayoutFiles(): FileCollection {
        return androidExtension.sourceSets["main"]
                .res
                .sourceFiles
                .filter { it.path.contains("/layout/") }
    }

    private fun getXmlParser(): DocumentBuilder {
        return DocumentBuilderFactory
                .newInstance()
                .apply {
                    isNamespaceAware = true
                }
                .newDocumentBuilder()
    }

    private fun log(msg: String) {
        if (kakaoMakerExtension.debug) {
            println("[$name] $msg")
        }
    }

}