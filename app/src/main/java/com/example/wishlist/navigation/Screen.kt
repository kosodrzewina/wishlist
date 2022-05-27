package com.example.wishlist.navigation

sealed class Screen(val route: String) {
    object HomeScreen : Screen(HOME_SCREEN)
    object ProductDetailScreen : Screen(PRODUCT_DETAIL_SCREEN)

    fun routeWithArgs(vararg args: String): String = buildString {
        append(route)
        args.forEach { append("/$it") }
    }
}