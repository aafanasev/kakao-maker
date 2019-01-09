package com.aafanasev.kakaomaker.util

object Helper {

    fun viewIdToName(id: String) = if (id.contains("_")) {
        id.split("_")
                .joinToString(separator = "", transform = { it.capitalize() })
                .decapitalize()
    } else {
        id
    }

}