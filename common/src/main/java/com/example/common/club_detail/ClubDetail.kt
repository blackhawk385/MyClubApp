package com.example.admin

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.admin.club_detail.ClubDetailRepository
import com.example.admin.club_detail.ClubDetailViewModel
import com.example.common.*
import com.example.common.data.AppState
import com.example.common.data.User
import com.example.common.persistance.SharedPreference

private var clubDetails: Club? = null
private var listOfPosts: List<Posts>? = null
private var listOfRequests: List<List<Request>>? = null
private var loggedInUser: User? = null

@Composable
fun ClubDetail(
    navController: NavHostController,
    uuid: String?,
    onAddNewPostClicked: (String) -> Unit,
    onUpdateClubDetailAdded: (String) -> Unit
) {

    val context = LocalContext.current

    val viewModel: ClubDetailViewModel = viewModel(factory = viewModelFactory {
        ClubDetailViewModel(ClubDetailRepository())
    })

    val showProgressBar = remember {
        mutableStateOf(false)
    }
    val club: State<AppState<Club>> = viewModel.clubState.collectAsState()
    val posts: State<AppState<List<Posts>>> = viewModel.postState.collectAsState()

    LaunchedEffect(key1 = Unit, block = {
        uuid?.let {
            viewModel.getClubDetails(it)
            viewModel.getClubPost(it)
            viewModel.getClubRequests(it)
        }

        SharedPreference(context).getUser {
            loggedInUser = it
        }
    })

    LaunchedEffect(key1 = club.value, block = {
        when (viewModel.clubState.value) {
            is AppState.Error -> showMessage(context, "Error while loading club details")
            is AppState.Idle -> {}
            is AppState.Loading -> showProgressBar.value = true
            is AppState.Success -> {
                showProgressBar.value = false
                clubDetails = viewModel.clubState.value.data
            }
        }
    })

    LaunchedEffect(key1 = posts.value, block = {
        when (viewModel.postState.value) {
            is AppState.Error -> showMessage(context, "Error while loading posts")
            is AppState.Idle -> {}
            is AppState.Loading -> showProgressBar.value = true
            is AppState.Success -> {
                showProgressBar.value = false
                listOfPosts = viewModel.postState.value.data
            }
        }
    })

    Column(modifier = Modifier.padding(20.dp)) {

        if (showProgressBar.value) {
            CustomCircularProgressBar()
        }
        Text(
            text = "Club Details",
            modifier = Modifier
                .padding(top = 10.dp)
                .align(CenterHorizontally),
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
        Spacer(modifier = Modifier.height(10.dp))

        clubDetails?.let {

            Row {
                Text(
                    text = "Club Name: ",
                    fontSize = 20.sp,
                    modifier = Modifier.alignByBaseline()
                )

                Text(
                    text = it.name,
                    fontSize = 12.sp,
                    modifier = Modifier.alignByBaseline(),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Row {
                Text(
                    text = "Owner Admin: ",
                    fontSize = 20.sp,
                    modifier = Modifier.alignByBaseline()
                )

                Text(
                    text = it.createdBy.fullName,
                    fontSize = 12.sp,
                    modifier = Modifier.alignByBaseline(),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Row {
                Text(
                    text = "Club Address: ",
                    fontSize = 20.sp,
                    modifier = Modifier.alignByBaseline()
                )

                Text(
                    text = it.location,
                    fontSize = 12.sp,
                    modifier = Modifier.alignByBaseline(),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

        DashboardTabView(
            tabTitles = arrayOf("Members", "Posts", "Requests"),
            modifier = Modifier.weight(4f)
        ) {
            when (it) {
                0 -> {
//                    clubDetails?.let { it1 -> ClubMembers(members = it1) }
                }
                1 -> listOfPosts?.let { it1 -> ClubPosts(posts = it1) }
                2 -> listOfRequests?.let { it1 -> ClubRequests(requests = it1) }
            }
        }

        Column(modifier = Modifier.weight(1f)) {
            ButtonControl(buttonText = "Add New Post", onClick = {
                clubDetails?.uuid?.let { onAddNewPostClicked(it) }
//                navController.navigate(AdminEnum.AddPost.name.plus("?uuid=${clubDetails?.uuid}"))
            })
            if (clubDetails?.createdBy?.uuid == loggedInUser?.uuid && loggedInUser?.isAdmin == true) {
                ButtonControl(buttonText = "Update Detail", onClick = {
                    clubDetails?.uuid?.let { onUpdateClubDetailAdded(it) }
//                    navController.navigate(AdminEnum.AddClub.name.plus("?uuid=${clubDetails?.uuid}"))
                })
            }
        }


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
fun ClubMembers(members: List<User>) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(members.size) {
            Column {
                Text(
                    text = members[it].fullName,
                    modifier = Modifier.padding(10.dp)
                )
                Divider(thickness = 2.dp)
            }

        }
    }
}

@Composable
fun ClubRequests(requests: List<List<Request>>) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(requests.size) {
            Column {
//                Text(
//                    text = requests[it],
//                    modifier = Modifier.padding(10.dp)
//                )
                Divider(thickness = 2.dp)
            }

        }
    }
}