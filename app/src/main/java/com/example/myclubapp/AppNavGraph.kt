package com.example.jetcomposenavmultimodule.main.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.common.data.User
import com.example.common.persistance.FirebaseUtil
import com.example.core.dependencyprovider.DependencyProvider
import com.example.core.feature_api.register
import com.example.myclubapp.loggedInUser


@Composable
fun AppNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    loggedinUser: User
) {
    NavHost(
        navController = navController,
        startDestination = navigateToStartDest(loggedinUser)
    ) {

        register(
            DependencyProvider.adminFeature(),
            navController = navController,
            modifier = modifier
        )

        register(
            DependencyProvider.memberFeature(),
            navController = navController,
            modifier = modifier
        )

        register(
            DependencyProvider.loginFeature(),
            navController = navController,
            modifier = modifier
        )
    }
}

fun navigateToStartDest(user: User): String {

    return if (FirebaseUtil.checkUserStatus() && user.isAdmin) {
        DependencyProvider.adminFeature().adminRoute

    } else if (FirebaseUtil.checkUserStatus()) {
        DependencyProvider.memberFeature().memberRoute

    } else {
        DependencyProvider.loginFeature().loginRoute
    }
}
