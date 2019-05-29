package dev.afanasev.kakaomaker.io

import dev.afanasev.kakaomaker.view.PageObjectView

interface FileSaver {

    fun save(screen: String, views: List<PageObjectView>)

}