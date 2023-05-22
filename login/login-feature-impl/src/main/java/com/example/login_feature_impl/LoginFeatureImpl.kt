package com.example.login_feature_impl

import androidx.navigation.compose.composable
import com.example.core.dependencyprovider.DependencyProvider
import com.example.login.LoginEnum
import com.example.login.LoginScreen
import com.example.login.RegisterScreen
import com.example.login_feature_api.LoginFeatureAPI

class LoginFeatureImpl : LoginFeatureAPI {
    override val loginRoute: String
        get() = "graph-auth"

    override fun registerGraph(
        navGraphBuilder: androidx.navigation.NavGraphBuilder,
        navController: androidx.navigation.NavHostController,
        modifier: androidx.compose.ui.Modifier
    ) {
        navGraphBuilder.composable(loginRoute) {
//            LoginScreen(navController)
            LoginScreen(navController,
                onNavigateToSettings = {
                    val admin = DependencyProvider.adminFeature()
                    navController.navigate(admin.adminRoute) {
                        popUpTo(loginRoute) { inclusive = true }
                    }
                }
            )
        }

        navGraphBuilder.composable(route = LoginEnum.RegisterScreen.name) {
            RegisterScreen(navController = navController)
        }
    }
}