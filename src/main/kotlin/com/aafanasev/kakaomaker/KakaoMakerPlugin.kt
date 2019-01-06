package com.aafanasev.kakaomaker

import com.aafanasev.kakaomaker.tasks.GenerateKakaoScreensTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.register

@Suppress("Unused")
class KakaoMakerPlugin : Plugin<Project> {

    companion object {
        const val EXTENSION_NAME = "kakaoMaker"
    }

    override fun apply(project: Project) {
        project.extensions["android"]
        project.extensions.create<KakaoMakerPluginExtension>(EXTENSION_NAME)

        project.tasks.register("generateKakaoScreens", GenerateKakaoScreensTask::class)
    }

}