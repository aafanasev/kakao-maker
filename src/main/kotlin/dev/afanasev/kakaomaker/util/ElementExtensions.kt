package dev.afanasev.kakaomaker.util

import org.w3c.dom.Element

private const val ATTR_ANDROID_NAMESPACE = "http://schemas.android.com/apk/res/android"
private const val ATTR_KAKAO_NAMESPACE = "http://schemas.android.com/tools"
private const val ATTR_ID = "id"
private const val ATTR_LAYOUT = "layout"
private const val ATTR_SCREEN = "kakaoScreen"
private const val ATTR_TYPE = "kakaoType"
private const val ATTR_IGNORE = "kakaoIgnore"

val Element.id: String
    get() = getAttributeNS(ATTR_ANDROID_NAMESPACE, ATTR_ID).substringAfterLast("/")

val Element.kakaoScreenName: String
    get() = getAttributeNS(ATTR_KAKAO_NAMESPACE, ATTR_SCREEN)

val Element.kakaoType: String
    get() = getAttributeNS(ATTR_KAKAO_NAMESPACE, ATTR_TYPE)

val Element.kakaoIgnore: Boolean
    get() = getAttributeNS(ATTR_KAKAO_NAMESPACE, ATTR_IGNORE).isNotEmpty()

val Element.isMergeTag: Boolean
    get() = tagName == "merge"

val Element.isIncludeTag: Boolean
    get() = tagName == "include"

val Element.layout: String
    get() = getAttribute(ATTR_LAYOUT).substringAfterLast("/")