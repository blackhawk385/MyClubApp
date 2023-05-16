package com.example.login

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.common.Graph


@Composable
fun LoginNavigation() {

    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = LoginEnum.LoginScreen.name, route = Graph.AUTH
    ) {

        composable(route = LoginEnum.LoginScreen.name) {
            LoginScreen(navController)
        }

        composable(route = LoginEnum.RegisterScreen.name) {
            RegisterScreen(navController = navController)
        }
    }
}

