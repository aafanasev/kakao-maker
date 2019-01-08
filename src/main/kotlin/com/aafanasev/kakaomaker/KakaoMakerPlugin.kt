package com.aafanasev.kakaomaker

import com.aafanasev.kakaomaker.tasks.GenerateKakaoScreensTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.DependencyResolutionListener
import org.gradle.api.artifacts.ResolvableDependencies
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.register

@Suppress("Unused")
class KakaoMakerPlugin : Plugin<Project> {

    companion object {
        const val EXTENSION_NAME = "kakaoMaker"
        private const val UI_TEST_CONFIGURATION = "androidTestImplementation"
        private const val KAKAO_DEPENDENCY_PATH = "com.agoda.kakao:kakao:1.4.0"
    }

    override fun apply(project: Project) {
        project.extensions["android"]
        project.extensions.create<KakaoMakerPluginExtension>(EXTENSION_NAME)

        project.gradle.addListener(object : DependencyResolutionListener {
            override fun beforeResolve(p0: ResolvableDependencies) {
                project.dependencies.add(UI_TEST_CONFIGURATION, KAKAO_DEPENDENCY_PATH)
                project.gradle.removeListener(this)
            }

            override fun afterResolve(p0: ResolvableDependencies) {}
        })

        project.tasks.register("generateKakaoScreens", GenerateKakaoScreensTask::class)
    }

}