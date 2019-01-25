package com.aafanasev.kakaomaker.util

internal fun <T> simpleLazy(initializer: () -> T): Lazy<T> = lazy(LazyThreadSafetyMode.NONE, initializer)

internal fun viewIdToName(viewId: String) = if (viewId.contains("_")) {
    viewId.split("_")
            .joinToString(separator = "", transform = { it.capitalize() })
            .decapitalize()
} else {
    viewId
}
