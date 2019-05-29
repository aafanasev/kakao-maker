package dev.afanasev.kakaomaker.view

sealed class PageObjectView {

    data class View(val id: String)
    data class TextView(val id: String)
    data class ImageView(val id: String)
    data class ButtonView(val id: String)
    data class IncludeView(val id: String, val layout: String)

}