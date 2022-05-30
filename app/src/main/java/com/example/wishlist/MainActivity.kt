package com.example.wishlist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.wishlist.database.ProductViewModel
import com.example.wishlist.navigation.NavGraph
import com.example.wishlist.ui.theme.WishlistTheme
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private lateinit var navHostController: NavHostController
    private lateinit var productViewModel: ProductViewModel

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        productViewModel = ViewModelProvider(this)[ProductViewModel::class.java]

        GlobalScope.launch {
            productViewModel.selectAllProducts().collect { products ->
                products.forEach { product ->
                    if (!ProductStore.products.any {
                            it.productIdImagePath == product.productIdImagePath
                        }) {
                        ProductStore.products.add(product)
                    }
                }
            }
        }

        setContent {
            WishlistTheme {
                navHostController = rememberNavController()
                NavGraph(navHostController = navHostController, productViewModel)
            }
        }
    }
}
