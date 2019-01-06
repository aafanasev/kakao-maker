package com.aafanasev.kakaomaker

import java.io.File

open class KakaoMakerPluginExtension {

    /**
     * Show debug logs
     */
    var debug = false

    /**
     * Output directory where generated screens will be saved
     */
    var output: File? = null

}