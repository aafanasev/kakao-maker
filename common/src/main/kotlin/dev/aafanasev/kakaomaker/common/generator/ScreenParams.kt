package dev.aafanasev.kakaomaker.common.generator

data class ScreenParams(
        /**
         * Screen class and file name
         */
        val screenName: String,

        /**
         * Screen class package name
         */
        val packageName: String,

        /**
         * Used to import R class
         */
        val applicationId: String
)