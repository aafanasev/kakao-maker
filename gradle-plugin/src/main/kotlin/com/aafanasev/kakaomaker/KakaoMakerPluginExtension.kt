package com.aafanasev.kakaomaker

import java.io.File

open class KakaoMakerPluginExtension {

    /**
     * Show debug logs
     */
    var debug = false

    /**
     * Output directory where generated screens will be saved. Required
     */
    var outputDir: File? = null

    /**
     * Application package, f.ex. "com.sample.app". If null or empty the value will be taken from [outputDir]
     */
    var applicationId: String? = null

    /**
     * Package name for generated classes. If null the value will be taken from [outputDir]
     */
    var packageName: String? = null

}