package com.example.member

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.common.ButtonControl
import com.example.common.DashboardTabView
import com.example.common.IMemberDashboard
import com.example.common.persistance.SharedPreference
import com.example.member.ui.theme.MyClubAppTheme

class MainActivity : ComponentActivity(), IMemberDashboard {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyClubAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    showDashboard()
                }
            }
        }
    }

    override fun showDashboard() {

    }
}

@Composable
fun MemberDashboard(navController: NavController) {

    Column(modifier = Modifier.fillMaxSize()) {
        DashboardTabView(
            tabTitles = arrayOf("My Joined Club", "My Club Post", "All Clubs"),
            modifier = Modifier.weight(2f)
        ) {

            //we can change data using launched effect having same screen
            when (it) {
                0 -> MyJoinedClub()
            }
        }
        Column(modifier = Modifier.weight(1f)) {
            Divider(color = Color.Blue)
        }

        ButtonControl(buttonText
        = "Profile", onClick = {
            navController.navigate(MemberEnum.UserDetail.name)
        })

        ButtonControl(buttonText = "Add New post", onClick = {
            navController.navigate(MemberEnum.AddPost.name) })
    }
}

@Composable
fun MyJoinedClub() {

}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyClubAppTheme {
    }
}