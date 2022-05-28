package com.example.wishlist.navigation

sealed class Screen(val route: String) {
    object HomeScreen : Screen(HOME_SCREEN)
    object ProductDetailScreen : Screen(PRODUCT_DETAIL_SCREEN)
    object CreateOrEditProductScreen : Screen(CREATE_OR_EDIT_PRODUCT_SCREEN)

    fun routeWithArgs(vararg args: String): String = buildString {
        append(route)
        args.forEach { append("/$it") }
    }
}