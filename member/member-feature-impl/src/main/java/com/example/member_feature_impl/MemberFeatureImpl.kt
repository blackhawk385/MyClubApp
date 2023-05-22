package com.example.member_feature_impl

import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.admin.MemberProfile
import com.example.member.AddPost
import com.example.member.ClubDetail
import com.example.member_feature_api.MemberFeatureAPI
import com.example.member_feature_impl.member.PostDetails
import com.example.member_feature_impl.member.dashboard.MemberDashboard
import com.example.member_feature_impl.member.navigation.MemberEnum
import com.example.member_feature_impl.member.user_detail.UserDetail

class MemberFeatureImpl : MemberFeatureAPI {
    override val memberRoute: String
        get() = "graph-member"

    override fun registerGraph(
        navGraphBuilder: androidx.navigation.NavGraphBuilder,
        navController: androidx.navigation.NavHostController,
        modifier: androidx.compose.ui.Modifier
    ) {

        navGraphBuilder.composable(memberRoute) {
            MemberDashboard(navController)
        }

        navGraphBuilder.composable(MemberEnum.MemberDashboard.name){
            MemberDashboard(navController = navController)
        }

        navGraphBuilder.composable(
            MemberEnum.AddPost.name.plus("?uuid={uuid}"), arguments = listOf(navArgument("uuid")
            {
                nullable = true
                defaultValue = null
                type = NavType.StringType
            })){
            AddPost(navController = navController, it.arguments?.getString("uuid"))
        }

        navGraphBuilder.composable(MemberEnum.PostDetails.name){
            PostDetails(navController = navController)
        }

        navGraphBuilder.composable(MemberEnum.MemberProfile.name){
            MemberProfile(navController = navController)
        }

        navGraphBuilder.composable(MemberEnum.ClubDetails.name.plus("/{uuid}")){
            ClubDetail(navController = navController, it.arguments?.getString("uuid"))
        }

        navGraphBuilder.composable(MemberEnum.UserDetail.name){
            UserDetail(navController = navController)
        }
    }

}