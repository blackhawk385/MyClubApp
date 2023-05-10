package com.example.admin

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.common.DashboardTabView

@Composable
fun ClubDetail(navController: NavHostController) {

    Column() {
        Text(text = "Club Name")
        Text(text = "actual club name")
        Text(text = "Owner Admin")
        Text(text = "actual club name")
        Text(text = "Club Address")
        Text(text = "actual club name")
        Text(text = "City")
        Text(text = "actual club name")
        Text(text = "Rooms")
        Text(text = "Room Charges")
        Text(text = "actual club name")


        DashboardTabView(tabTitles = arrayOf("Members", "Posts", "Requests")) {

        }
    }
}