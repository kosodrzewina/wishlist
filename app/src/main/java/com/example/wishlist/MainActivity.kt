package com.example.wishlist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.wishlist.navigation.NavGraph
import com.example.wishlist.ui.theme.WishlistTheme

class MainActivity : ComponentActivity() {
    private lateinit var navHostController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WishlistTheme {
                navHostController = rememberNavController()
                NavGraph(navHostController = navHostController)
            }
        }
    }
}
