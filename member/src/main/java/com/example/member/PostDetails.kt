package com.example.member

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.common.DashboardTabView

@Composable
fun PostDetails(navController: NavHostController) {
    Column() {
        Text(text = "Post title")
        Text(text = "actual club name")
        Text(text = "Authored by")
        Text(text = "actual club name")
        Text(text = "club")
        Text(text = "actual club name")
        Text(text = "City")
        Text(text = "actual club name")
        Text(text = "Description")
        Text(text = "actual club name")

        //video view


        DashboardTabView(tabTitles = arrayOf("Comments")) {

        }
    }
}