package com.example.member

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.admin.MemberProfile
import com.example.common.Graph
import com.example.member.user_detail.UserDetail

@Composable
fun MemberNavigation(){

    val navController = rememberNavController()

    NavHost(navController = navController,
        startDestination = MemberEnum.MemberDashboard.name, route = Graph.MEMBER){

        composable(MemberEnum.MemberDashboard.name){
            MemberDashboard(navController = navController)
        }

        composable(MemberEnum.AddPost.name){
            AddPost(navController = navController)
        }

        composable(MemberEnum.PostDetails.name){
            PostDetails(navController = navController)
        }

        composable(MemberEnum.MemberProfile.name){
            MemberProfile(navController = navController)
        }

        composable(MemberEnum.ClubDetails.name){
            ClubDetail(navController = navController)
        }

        composable(MemberEnum.UserDetail.name){
            UserDetail(navController = navController)
        }
    }
}