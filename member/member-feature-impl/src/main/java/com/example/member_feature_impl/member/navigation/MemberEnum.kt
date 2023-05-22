package com.example.member_feature_impl.member.navigation

enum class MemberEnum {
    MemberDashboard,
    MemberProfile,
    ClubDetails,
    UserDetail,
    PostDetails,
    AddPost;

    companion object {
        fun fromRoute(route: String?): MemberEnum =
            when (route?.substringBefore("/")) {
                MemberDashboard.name -> MemberDashboard
                MemberProfile.name -> MemberProfile
                ClubDetails.name -> ClubDetails
                PostDetails.name -> PostDetails
                UserDetail.name -> UserDetail
                AddPost.name -> AddPost
                null -> MemberProfile
                else -> throw java.lang.IllegalArgumentException("$route is not defined")
            }
    }

}