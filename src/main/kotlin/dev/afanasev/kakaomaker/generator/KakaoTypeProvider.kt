package dev.afanasev.kakaomaker.generator

import com.agoda.kakao.bottomnav.KBottomNavigationView
import com.agoda.kakao.common.views.KBaseView
import com.agoda.kakao.common.views.KSwipeView
import com.agoda.kakao.common.views.KView
import com.agoda.kakao.drawer.KDrawerView
import com.agoda.kakao.edit.KTextInputLayout
import com.agoda.kakao.image.KImageView
import com.agoda.kakao.pager.KViewPager
import com.agoda.kakao.swiperefresh.KSwipeRefreshLayout
import com.agoda.kakao.text.KButton
import com.agoda.kakao.text.KTextView
import kotlin.reflect.KClass

internal class KakaoTypeProvider {

    companion object {
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
    }

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