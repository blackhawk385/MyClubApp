package com.example.admin

enum class AdminEnum {
    AdminDashboardScreen,
    AdminProfile,
    ClubDetails,
    PostDetails,
    AddPost,
    UserDetail,
    UpdateClub,
    AddClub;

    companion object {
        fun fromRoute(route: String?) : AdminEnum =
            when(route?.substringBefore("/")) {
                AdminDashboardScreen.name -> AdminDashboardScreen
                AdminProfile.name -> AdminProfile
                ClubDetails.name -> ClubDetails
                PostDetails.name -> PostDetails
                UserDetail.name -> UserDetail
                AddPost.name -> AddPost
                AddClub.name -> AddClub
                UpdateClub.name -> UpdateClub
                null -> AdminDashboardScreen
                else -> throw java.lang.IllegalArgumentException("$route is not defined")
            }
    }
}