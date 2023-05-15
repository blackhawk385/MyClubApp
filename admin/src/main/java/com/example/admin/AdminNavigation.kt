package com.example.admin

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.admin.dashboard.AdminDashboard
import com.example.admin.update_club.UpdateClub
import com.example.admin.user_detail.UserDetail
import com.example.common.Graph

@Composable
fun AdminNavigation() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = AdminEnum.AdminDashboardScreen.name, route = Graph.ADMIN
    ) {

        composable(AdminEnum.AdminDashboardScreen.name) {
            AdminDashboard(navController = navController)
        }

        composable(AdminEnum.AddClub.name.plus("?uuid={uuid}"), arguments = listOf(navArgument("uuid")
        {
            nullable = true
            defaultValue = null
            type = NavType.StringType
        })) {
            AddClub(navController = navController, it.arguments?.getString("uuid"))
        }

        composable(
            AdminEnum.AddPost.name.plus("?uuid={uuid}"), arguments = listOf(navArgument("uuid")
            {
                nullable = true
                defaultValue = null
                type = NavType.StringType
            })
        ) {
            AddPost(navController = navController, it.arguments?.getString("uuid") )
        }

        composable(AdminEnum.AdminProfile.name) {
            AdminProfile(navController = navController)
        }
        composable(AdminEnum.ClubDetails.name.plus("/{uuid}")) {
            ClubDetail(navController = navController, it.arguments?.getString("uuid"))
        }

        composable(AdminEnum.PostDetails.name.plus("/{uuid}")) {
            PostDetails(navController = navController, it.arguments?.getString("uuid"))
        }

        composable(AdminEnum.UserDetail.name) {
            UserDetail(navController = navController)
        }

        composable(AdminEnum.UpdateClub.name) {
            UpdateClub(navController = navController)
        }
    }
}
