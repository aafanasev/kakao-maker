package com.aafanasev.kakaomaker.util

import org.w3c.dom.Element

internal const val ATTR_ANDROID_NAMESPACE = "http://schemas.android.com/apk/res/android"
internal const val ATTR_KAKAO_NAMESPACE = "http://schemas.android.com/tools"
internal const val ATTR_ID = "id"
internal const val ATTR_LAYOUT = "layout"
internal const val ATTR_SCREEN = "kakaoScreen"
internal const val ATTR_TYPE = "kakaoType"
internal const val ATTR_IGNORE = "kakaoIgnore"

internal val Element.id: String
    get() = getAttributeNS(ATTR_ANDROID_NAMESPACE, ATTR_ID).substringAfterLast("/")

internal val Element.kakaoScreenName: String
    get() = getAttributeNS(ATTR_KAKAO_NAMESPACE, ATTR_SCREEN)

internal val Element.kakaoType: String
    get() = getAttributeNS(ATTR_ANDROID_NAMESPACE, ATTR_TYPE)

internal val Element.kakaoIgnore: Boolean
    get() = getAttributeNS(ATTR_ANDROID_NAMESPACE, ATTR_IGNORE).isNotEmpty()

internal val Element.isMergeTag: Boolean
    get() = tagName == "merge"

internal val Element.isIncludeTag: Boolean
    get() = tagName == "include"

internal val Element.layout: String
    get() = getAttribute(ATTR_LAYOUT).substringAfterLast("/")