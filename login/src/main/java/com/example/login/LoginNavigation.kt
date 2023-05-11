package com.example.login

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.example.common.Graph
import com.example.common.persistance.SharedPreference
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


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

fun checkUserStatus(): Boolean {
    return Firebase.auth.currentUser != null
}