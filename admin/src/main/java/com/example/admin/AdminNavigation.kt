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
import com.example.common.Club
import com.example.common.Graph
import com.example.common.persistance.clubToJson
import com.example.common.persistance.jsonToClubObject

@Composable
fun AdminNavigation(){

    val navController = rememberNavController()

    NavHost(navController = navController,
        startDestination = AdminEnum.AdminDashboardScreen.name, route = Graph.ADMIN){

        composable(AdminEnum.AdminDashboardScreen.name){
            AdminDashboard(navController = navController)
        }

        composable(AdminEnum.AddClub.name){
            AddClub(navController = navController)
        }

        composable(AdminEnum.AddPost.name){
            AddPost(navController = navController)
        }

        composable(AdminEnum.AdminProfile.name){
            AdminProfile(navController = navController)
        }
//        + "/{club}"
        composable(AdminEnum.ClubDetails.name , arguments = listOf(
            navArgument("club") {
                type = NavType.StringType
                defaultValue = "Default"
            }
        )){
            ClubDetail(navController = navController)
        }

        composable(AdminEnum.PostDetails.name){
            PostDetails(navController = navController)
        }

        composable(AdminEnum.UserDetail.name){
            UserDetail(navController = navController)
        }

        composable(AdminEnum.UpdateClub.name){
            UpdateClub(navController = navController)
        }
    }
}
