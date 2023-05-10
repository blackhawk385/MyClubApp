package com.example.admin.dashboard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.admin.AdminEnum
import com.example.admin.ui.theme.MyClubAppTheme
import com.example.common.*
import com.example.common.data.User
import com.example.common.persistance.SharedPreference
import com.example.common.persistance.clubToJson

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

                }
            }
        }
    }

    override fun showDashboard() {
    }
}

private var user: User? = null
private var clubList: List<Club>? = null
private var myClubList: List<Club>? = null
private var myPostList: List<Posts>? = null
private var postList: List<Posts>? = null

@Composable
fun AdminDashboard(navController: NavController) {

    val viewModel: AdminDashboardViewModel = viewModel(factory = viewModelFactory {
        AdminDashboardViewModel(AdminRepository())
    })
    clubList = viewModel.state.collectAsState().value
    postList = viewModel.postState.collectAsState().value

    myClubList = clubList?.filter { club ->
        club.createdBy.uuid == user?.uuid
    }

    myPostList = postList?.filter { post ->
        post.associateClub.uuid == user?.uuid
    }

    val context = LocalContext.current
    LaunchedEffect(Unit, block = {
        SharedPreference(context).getUser {
            user = it
            viewModel.getMyClubs()
            viewModel.getMyPosts()
        }
    })

    Column(modifier = Modifier.fillMaxSize()) {
        DashboardTabView(
            tabTitles = arrayOf("My Clubs", "My Request", "All Clubs", "Club Posts"),
            modifier = Modifier.weight(5f)
        ) {
            when (it) {
                0 -> myClubList?.let { it1 -> MyClubs(navController, it1) }
                1 -> MyRequests()
                2 -> clubList?.let { it1 -> AllClubs(it1) }
                3 -> postList?.let { it1 -> ClubPosts(it1) }
            }
        }

        Column(modifier = Modifier.weight(1f)) {
            Divider(color = Color.Blue)
        }


        ButtonControl(modifier = Modifier.align(CenterHorizontally), buttonText = "My Profile", onClick = {
            navController.navigate(AdminEnum.UserDetail.name)
        })

        ButtonControl(modifier = Modifier.align(CenterHorizontally),buttonText = "Add New post", onClick = {
            navController.navigate(AdminEnum.AddPost.name)
        })

        ButtonControl(modifier = Modifier.align(CenterHorizontally).padding(bottom = 20.dp),buttonText = "Add New Club", onClick = {
            navController.navigate(AdminEnum.AddClub.name)
        })
    }
}

@Composable
fun ClubPosts(posts: List<Posts>) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(posts.size) {
            Column {
                Text(
                    text = posts[it].title,
                    modifier = Modifier.padding(10.dp)
                )
                Divider(thickness = 2.dp)
            }

        }
    }
}

@Composable
fun AllClubs(clubList: List<Club>) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(clubList.size) {
            Column {
                Text(text = clubList[it].name, modifier = Modifier.padding(10.dp))
                Divider(thickness = 2.dp)
            }
        }
    }
}

@Composable
fun MyRequests() {

}

@Composable
fun MyClubs(navController: NavController, myClubList: List<Club>) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(16.dp)
    ) {
        myClubList.size.let {
            items(it) {
                Column {
                    Text(
                        text = myClubList[it].name,
                        modifier = Modifier.padding(10.dp).clickable {
                            navController.navigate(AdminEnum.ClubDetails.name)
                        }, fontWeight = FontWeight.Bold
                    )
                    Divider(thickness = 2.dp)
                }
            }
        }
    }
}
