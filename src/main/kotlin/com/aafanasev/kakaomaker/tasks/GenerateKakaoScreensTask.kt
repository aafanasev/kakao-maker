package com.aafanasev.kakaomaker.tasks

import com.aafanasev.kakaomaker.KakaoMakerPlugin
import com.aafanasev.kakaomaker.KakaoMakerPluginExtension
import com.android.build.gradle.BaseExtension
import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.file.FileCollection
import org.gradle.api.tasks.TaskAction
import org.gradle.kotlin.dsl.get
import java.io.File
import javax.xml.parsers.DocumentBuilder
import javax.xml.parsers.DocumentBuilderFactory

open class GenerateKakaoScreensTask : DefaultTask() {

    companion object {
        const val ATTR_NAMESPACE = "http://schemas.android.com/tools"
        const val ATTR_SCREEN = "kakaoScreen"
        const val ATTR_TYPE = "kakaoType"
        const val ATTR_IGNORE = "kakaoIgnore"
    }

    private val androidExtension = project.extensions["android"] as BaseExtension
    private val kakaoMakerExtension = project.extensions[KakaoMakerPlugin.EXTENSION_NAME] as KakaoMakerPluginExtension

    @TaskAction
    fun action() {
        ensureOutputDir()

        val layouts = getLayoutFiles()
        val xmlParser = getXmlParser()

        layouts.forEach {
            val document = xmlParser.parse(it)

            if (document.documentElement.tagName != "merge") {
                val screenName = document.documentElement.getAttributeNS(ATTR_NAMESPACE, ATTR_SCREEN)

                if (screenName.isNotEmpty()) {
                    log(screenName)
                }
            }
        }
    }

    private fun ensureOutputDir() {
        val outputDir: File = kakaoMakerExtension.output ?: throw GradleException("Output path is not specified")

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
            println(msg)
        }
    }

}