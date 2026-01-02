package com.example.fashionmarket.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.fashionmarket.ui.screens.auth.LoginScreen
import com.example.fashionmarket.ui.screens.auth.RegisterScreen
import com.example.fashionmarket.ui.screens.splash.SplashScreen

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Login : Screen("login")
    object Register : Screen("register")
    object Home : Screen("home")
    object Products : Screen("products")
    object Cart : Screen("cart")
    object Profile : Screen("profile")
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    var startDestination by remember { mutableStateOf(Screen.Splash.route) }

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Screen.Splash.route) {
            SplashScreen(
                navController = navController
            )
        }

        composable(Screen.Login.route) {
            LoginScreen(
                navController = navController
            )
        }

        composable(Screen.Register.route) {
            RegisterScreen(
                navController = navController
            )
        }

        // We'll add other screens later
    }
}