package com.example.member

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.admin.MemberProfile
import com.example.common.Graph
import com.example.member_feature_impl.member.PostDetails
import com.example.member_feature_impl.member.dashboard.MemberDashboard
import com.example.member_feature_impl.member.navigation.MemberEnum
import com.example.member_feature_impl.member.user_detail.UserDetail

@Composable
fun MemberNavigation(){

    val navController = rememberNavController()

    NavHost(navController = navController,
        startDestination = MemberEnum.MemberDashboard.name, route = Graph.MEMBER){

        composable(MemberEnum.MemberDashboard.name){
            MemberDashboard(navController = navController)
        }

        composable(
            MemberEnum.AddPost.name.plus("?uuid={uuid}"), arguments = listOf(navArgument("uuid")
        {
            nullable = true
            defaultValue = null
            type = NavType.StringType
        })){
            AddPost(navController = navController, it.arguments?.getString("uuid"))
        }

        composable(MemberEnum.PostDetails.name){
            PostDetails(navController = navController)
        }

        composable(MemberEnum.MemberProfile.name){
            MemberProfile(navController = navController)
        }

        composable(MemberEnum.ClubDetails.name.plus("/{uuid}")){
            ClubDetail(navController = navController, it.arguments?.getString("uuid"))
        }

        composable(MemberEnum.UserDetail.name){
            UserDetail(navController = navController)
        }
    }
}