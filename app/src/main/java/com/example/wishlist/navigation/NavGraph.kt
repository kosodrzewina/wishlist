package com.example.wishlist.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.wishlist.MainActivity
import com.example.wishlist.Product
import com.example.wishlist.ProductStore
import com.example.wishlist.database.ProductViewModel
import com.example.wishlist.screens.HomeScreen
import com.example.wishlist.screens.ProductDetailScreen
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

@Composable
fun NavGraph(
    navHostController: NavHostController,
    productViewModel: ProductViewModel,
    mainActivity: MainActivity
) {
    NavHost(navController = navHostController, startDestination = Screen.HomeScreen.route) {
        composable(route = Screen.HomeScreen.route) {
            HomeScreen(
                products = ProductStore.products,
                navController = navHostController,
                mainActivity
            )
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
            val product: Product = try {
                ProductStore.products.first {
                    it.productIdImagePath == decodedProductId
                }
            } catch (e: NoSuchElementException) {
                Product("", "", "", 0.0, 0.0)
            }

            ProductDetailScreen(
                product = product,
                navController = navHostController,
                productViewModel
            )
        }
    }
}