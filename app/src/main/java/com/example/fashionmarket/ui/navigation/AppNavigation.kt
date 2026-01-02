package com.example.fashionmarket.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.fashionmarket.ui.screens.auth.LoginScreen
import com.example.fashionmarket.ui.screens.auth.RegisterScreen
import com.example.fashionmarket.ui.screens.cart.CartScreen
import com.example.fashionmarket.ui.screens.home.HomeScreen
import com.example.fashionmarket.ui.screens.profile.ProfileScreen
import com.example.fashionmarket.ui.screens.search.SearchScreen
import com.example.fashionmarket.ui.screens.splash.SplashScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        composable(Screen.Splash.route) {
            SplashScreen(navController = navController)
        }

        composable(Screen.Login.route) {
            LoginScreen(navController = navController)
        }

        composable(Screen.Register.route) {
            RegisterScreen(navController = navController)
        }

        composable(Screen.Home.route) {
            HomeScreen(navController = navController)
        }

        composable(Screen.Cart.route) {
            CartScreen(navController = navController)
        }

        composable(Screen.Profile.route) {
            ProfileScreen(navController = navController)
        }

        // ADD THIS - Search Screen route
        composable(Screen.Products.route) {
            SearchScreen(navController = navController)
        }
    }
}