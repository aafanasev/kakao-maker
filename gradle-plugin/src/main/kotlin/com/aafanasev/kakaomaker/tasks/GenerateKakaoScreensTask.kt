package com.aafanasev.kakaomaker.tasks

import com.aafanasev.kakaomaker.KakaoMakerPlugin
import com.aafanasev.kakaomaker.KakaoMakerPluginExtension
import com.android.build.gradle.BaseExtension
import dev.aafanasev.kakaomaker.common.generator.KakaoScreenGenerator
import dev.aafanasev.kakaomaker.common.generator.ScreenParams
import dev.aafanasev.kakaomaker.common.util.isMergeTag
import dev.aafanasev.kakaomaker.common.util.kakaoScreenName
import dev.aafanasev.kakaomaker.common.util.simpleLazy
import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.tasks.TaskAction
import java.io.File
import javax.xml.parsers.DocumentBuilder
import javax.xml.parsers.DocumentBuilderFactory

open class GenerateKakaoScreensTask : DefaultTask() {

    private val androidExtension by simpleLazy {
        project.extensions.getByName("android") as BaseExtension
    }
    private val kakaoMakerExtension by simpleLazy {
        project.extensions.getByName(KakaoMakerPlugin.EXTENSION_NAME) as KakaoMakerPluginExtension
    }

    @TaskAction
    @Suppress("unused")
    fun generateKakaoScreens() {
        ensureOutputDir()

        val ext = project.findProject("mylibrary")?.extensions?.getByName("android")
        (ext as? BaseExtension)?.sourceSets?.getByName("main")?.res?.sourceFiles
                ?.filter { it.isDirectory }
                ?.forEach {
                    println("asd $it")
                }

        val parser = createXmlParser()

        androidExtension.sourceSets.getByName("main")
                .res
                .sourceFiles
                .filter { it.path.contains("/layout/") }
                .mapNotNull {
                    val document = parser.parse(it)
                    val isMergeTag = document.documentElement.isMergeTag
                    val screenName = document.documentElement.kakaoScreenName
                    parser.reset()

                    if (isMergeTag || screenName.isEmpty()) {
                        null
                    } else {
                        Pair(screenName, it)
                    }
                }
                .filter { false }
                .forEach { (screenName, layoutFile) ->
                    val screenOptions = ScreenParams(
                            screenName,
                            packageName = kakaoMakerExtension.packageName!!,
                            applicationId = kakaoMakerExtension.applicationId!!
                    )

                    val file = KakaoScreenGenerator().generate(
                            screenOptions,
                            layoutFile,
                            sortedSetOf()
                    )

                    File(kakaoMakerExtension.outputDir, "todo").writeText(file)
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

    private fun createXmlParser(): DocumentBuilder {
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