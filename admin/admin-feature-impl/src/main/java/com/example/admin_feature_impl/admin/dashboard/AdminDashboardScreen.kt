package com.example.admin.dashboard

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.admin.AdminEnum
import com.example.common.*
import com.example.common.data.AppState
import com.example.common.data.User
import com.example.common.persistance.FirebaseUtil
import com.example.common.persistance.SharedPreference
import com.example.core.dependencyprovider.DependencyProvider


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
    val context = LocalContext.current
    val myClubState = viewModel.state.collectAsState()
    postList = viewModel.postState.collectAsState().value

    val showProgressBar = remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = myClubState.value, block = {
        when(myClubState.value){
            is AppState.Error -> showMessage(context = context, "Error while loading data")
            is AppState.Idle -> {}
            is AppState.Loading -> {
                showProgressBar.value = true
            }
            is AppState.Success -> {
                showProgressBar.value = false
                clubList = myClubState.value.data
            }
        }
    })



    myClubList = clubList?.filter { club ->
        club.createdBy.uuid == user?.uuid
    }

    myPostList = postList?.filter { post ->
        post.associateClub.uuid == user?.uuid
    }



    LaunchedEffect(Unit, block = {
        SharedPreference(context).getUser {
            user = it
            viewModel.getMyClubs()
            viewModel.getMyPosts()
        }
    })

    Column(modifier = Modifier.fillMaxSize()) {
        if (showProgressBar.value) {
            CustomCircularProgressBar()
        }
        DashboardTabView(
            tabTitles = arrayOf("My Clubs", "My Request", "All Clubs", "Club Posts"),
            modifier = Modifier.weight(10f)
        ) {
            when (it) {
                0 -> myClubList?.let { it1 -> MyClubs(navController, it1) }
                1 -> MyRequests()
                2 -> clubList?.let { it1 -> AllClubs(navController, it1) }
                3 -> postList?.let { it1 -> ClubPosts(navController, it1) }
            }
        }


        Column(modifier = Modifier.weight(1f)) {
            Divider(color = Color.Blue)
        }

        ButtonControl(
            modifier = Modifier.align(CenterHorizontally),
            buttonText = "My Profile",
            onClick = {
                navController.navigate(AdminEnum.UserDetail.name)
            })

        ButtonControl(
            modifier = Modifier.align(CenterHorizontally),
            buttonText = "Add New post",
            onClick = {
                navController.navigate(AdminEnum.AddPost.name)
            })

        ButtonControl(
            modifier = Modifier
                .align(CenterHorizontally)
                .padding(bottom = 20.dp),
            buttonText = "Add New Club",
            onClick = {
                navController.navigate(AdminEnum.AddClub.name)
            })

        LogoutButtonControl(modifier = Modifier.align(Alignment.End).clip(CircleShape)){
            FirebaseUtil.logoutFirebaseUser()
            showMessage(context, "Logged Out")
            val login = DependencyProvider.loginFeature()
            navController.navigate(login.loginRoute) {
                popUpTo("admin-route") { inclusive = true }
            }
        }
    }
}

@Composable
fun ClubPosts(navController: NavController, posts: List<Posts>) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(posts.size) {
            Column {
                Text(
                    text = posts[it].title,
                    modifier = Modifier
                        .padding(10.dp)
                        .clickable {
                            navController.navigate(AdminEnum.PostDetails.name.plus("/${posts?.get(it)?.uuid}"))
                        },
                    fontSize = 14.sp
                )
                Divider(thickness = 2.dp)
            }

        }
    }
}

@Composable
fun AllClubs(navController: NavController, clubList: List<Club>) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(clubList.size) {
            Column {
                Text(text = clubList[it].name, modifier = Modifier
                    .padding(10.dp)
                    .clickable {
                        navController.navigate(AdminEnum.ClubDetails.name.plus("/${clubList?.get(it)?.uuid}"))
                    })
                Divider(thickness = 2.dp)
            }
        }
    }
}

@Composable
fun MyRequests() {
//    requests: List<Request>
//    LazyColumn(
//        modifier = Modifier.fillMaxWidth(),
//        contentPadding = PaddingValues(16.dp)
//    ) {
//        items(requests.size) {
//            Column {
//                Text(
//                    text = requests[it].requestedBy.fullName,
//                    modifier = Modifier.padding(10.dp)
//                )
//                Divider(thickness = 2.dp)
//            }
//
//        }
//    }
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
                        modifier = Modifier
                            .padding(10.dp)
                            .clickable {
                                navController.navigate(
                                    AdminEnum.ClubDetails.name.plus(
                                        "/${
                                            clubList?.get(
                                                it
                                            )?.uuid
                                        }"
                                    )
                                )
                            }
                    )
                    Divider(thickness = 2.dp)
                }
            }
        }
    }
}
