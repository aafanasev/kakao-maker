package com.aafanasev.kakaomaker.tasks

import com.aafanasev.kakaomaker.KakaoMakerPlugin
import com.aafanasev.kakaomaker.KakaoMakerPluginExtension
import com.android.build.gradle.BaseExtension
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.gradle.kotlin.dsl.get

open class GenerateKakaoScreensTask : DefaultTask() {

    private val androidExtension = project.extensions["android"] as BaseExtension
    private val kakaoMakerExtension = project.extensions[KakaoMakerPlugin.EXTENSION_NAME] as KakaoMakerPluginExtension

    @TaskAction
    fun action() {
        val layouts = androidExtension.sourceSets["main"]
                .res
                .sourceFiles
                .filter { it.path.contains("/layout/") }

        // TODO:
    }

}