package com.aafanasev.kakaomaker

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import java.io.File

open class GenerateKakaoScreensTask(
        val layout: Set<File>,
        val output: File
) : DefaultTask() {

    @TaskAction
    fun action() {
        println("hello")
    }

}