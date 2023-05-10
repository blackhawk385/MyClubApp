package com.example.myclubapp

import com.example.login.LoginEnum

enum class MyClubEnum {
    LoginScreen,
    DashboardScreen;

    companion object {
        fun fromRoute(route: String?) : MyClubEnum =
            when(route?.substringBefore("/")) {
                MyClubEnum.LoginScreen.name -> MyClubEnum.LoginScreen
                MyClubEnum.DashboardScreen.name -> MyClubEnum.DashboardScreen
                null -> LoginScreen
                else -> throw java.lang.IllegalArgumentException("$route is not defined")
            }
    }
}