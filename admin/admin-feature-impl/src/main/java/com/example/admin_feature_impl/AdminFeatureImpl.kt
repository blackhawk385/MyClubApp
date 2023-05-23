package com.example.admin_feature_impl

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.admin.*
import com.example.admin.dashboard.AdminDashboard
import com.example.admin.update_club.UpdateClub
import com.example.admin.user_detail.UserDetail
import com.example.admin_feature_api.AdminFeatureAPI
import com.example.common.ButtonControl

class AdminFeatureImpl : AdminFeatureAPI {
    override val adminRoute: String
        get() = "admin-route"

    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController,
        modifier: Modifier
    ) {
        navGraphBuilder.composable(adminRoute) {
            AdminDashboard(navController)
        }

        navGraphBuilder.composable(
            AdminEnum.AddClub.name.plus("?uuid={uuid}"), arguments = listOf(navArgument("uuid")
            {
                nullable = true
                defaultValue = null
                type = NavType.StringType
            })
        ) {
            AddClub(navController = navController, it.arguments?.getString("uuid"))
        }

        navGraphBuilder.composable(
            AdminEnum.AddPost.name.plus("?uuid={uuid}"), arguments = listOf(navArgument("uuid")
            {
                nullable = true
                defaultValue = null
                type = NavType.StringType
            })
        ) {
            AddPost(navController = navController, it.arguments?.getString("uuid"))
        }

        navGraphBuilder.composable(AdminEnum.AdminProfile.name) {
            AdminProfile(navController = navController)
        }
        navGraphBuilder.composable(AdminEnum.ClubDetails.name.plus("/{uuid}")) {
            ClubDetail(
                navController = navController,
                it.arguments?.getString("uuid"),
                onAddNewPostClicked = { uuid ->
                    navController.navigate(AdminEnum.AddPost.name.plus("?uuid=${uuid}"))
                },
                onUpdateClubDetailAdded = { uuid ->
                    navController.navigate(AdminEnum.AddClub.name.plus("?uuid=${uuid}"))
                })
        }

        navGraphBuilder.composable(AdminEnum.PostDetails.name.plus("/{uuid}")) {
            PostDetails(navController = navController, it.arguments?.getString("uuid"))
        }

        navGraphBuilder.composable(AdminEnum.UserDetail.name) {
            UserDetail(navController = navController, {
                navController.navigate(AdminEnum.AdminProfile.name)
            })
        }

        navGraphBuilder.composable(AdminEnum.UpdateClub.name) {
            UpdateClub(navController = navController)
        }
    }
}