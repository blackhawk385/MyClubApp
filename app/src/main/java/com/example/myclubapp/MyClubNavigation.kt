package com.example.myclubapp

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.common.DashboardTabView
import com.example.login.LoginEnum
import com.example.login.LoginScreen
import com.example.login.RegisterScreen


@Composable
fun MyClubNavigation(){

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = LoginEnum.LoginScreen.name){

        composable(MyClubEnum.LoginScreen.name){
//            LoginNavigation()
        }

        composable(MyClubEnum.DashboardScreen.name){
//            DashboardTabView(navController = navController)
        }
    }
}
