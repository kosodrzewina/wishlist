package com.example.wishlist.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.wishlist.ProductStore
import com.example.wishlist.screens.HomeScreen
import com.example.wishlist.screens.ProductDetailScreen

@Composable
fun NavGraph(navHostController: NavHostController) {
    NavHost(navController = navHostController, startDestination = Screen.HomeScreen.route) {
        composable(route = Screen.HomeScreen.route) {
            HomeScreen(products = ProductStore.products, navController = navHostController)
        }

        composable(
            route = Screen.ProductDetailScreen.route + "/{productId}",
            arguments = listOf(
                navArgument("productId") {
                    type = NavType.IntType
                    nullable = false
                }
            )
        ) { entry ->
            ProductDetailScreen(
                product = ProductStore.products.first { product ->
                    product.id == entry.arguments?.getInt("productId")
                },
                navController = navHostController
            )
        }
    }
}