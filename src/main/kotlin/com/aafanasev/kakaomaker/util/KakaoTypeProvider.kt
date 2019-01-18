package com.aafanasev.kakaomaker.util

import com.agoda.kakao.*
import kotlin.reflect.KClass

object KakaoTypeProvider {

    private val BUILT_IN_TYPES = setOf<KClass<out KBaseView<*>>>(
            KTextView::class,
            KButton::class,
            KImageView::class,
            KTextInputLayout::class,
            KSwipeRefreshLayout::class,
            KSwipeView::class,
            KViewPager::class,
            KBottomNavigationView::class,
            KDrawerView::class,
            KView::class
    )

    fun getType(tagName: String): KClass<out KBaseView<*>> {
        for (type in BUILT_IN_TYPES) {
            val name = type.simpleName!!.substring(1)
            if (tagName.endsWith(name)) {
                return type
            }
        }

        return KView::class
    }

}