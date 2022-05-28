package com.example.wishlist.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.wishlist.ProductStore
import com.example.wishlist.screens.CreateOrEditProductScreen
import com.example.wishlist.screens.HomeScreen
import com.example.wishlist.screens.ProductDetailScreen
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

@Composable
fun NavGraph(navHostController: NavHostController) {
    NavHost(navController = navHostController, startDestination = Screen.HomeScreen.route) {
        composable(route = Screen.HomeScreen.route) {
            HomeScreen(products = ProductStore.products, navController = navHostController)
        }

        composable(
            route = Screen.ProductDetailScreen.route + "/{encodedProductId}",
            arguments = listOf(
                navArgument("encodedProductId") {
                    type = NavType.StringType
                    nullable = false
                }
            )
        ) { entry ->
            val decodedProductId = URLDecoder.decode(
                entry.arguments?.getString("encodedProductId"),
                StandardCharsets.UTF_8.toString()
            )

            ProductDetailScreen(
                product = ProductStore.products.first { product ->
                    product.productIdImagePath == decodedProductId
                },
                navController = navHostController
            )
        }

        composable(
            route = Screen.CreateOrEditProductScreen.route + "/{encodedProductId}",
            arguments = listOf(
                navArgument("encodedProductId") {
                    type = NavType.StringType
                    nullable = false
                }
            )
        ) { entry ->
            val decodedProductId = URLDecoder.decode(
                entry.arguments?.getString("encodedProductId"),
                StandardCharsets.UTF_8.toString()
            )

            CreateOrEditProductScreen(
                product = ProductStore.products.first { product ->
                    product.productIdImagePath == decodedProductId
                },
                navController = navHostController
            )
        }
    }
}