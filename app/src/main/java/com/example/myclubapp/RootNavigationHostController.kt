package com.example.myclubapp

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.common.Graph
import com.example.member.MemberNavigation


@Composable
fun RootNavigationHostController(){

    val navController = rememberNavController()

    NavHost(navController = navController, route = Graph.MAIN, startDestination = Graph.AUTH){
        composable(route = Graph.AUTH){
//            LoginNavigation()
        }
        composable(route = Graph.ADMIN){
//            AdminNavigation()
        }
        composable(route = Graph.MEMBER){
            MemberNavigation()
        }
    }
}


