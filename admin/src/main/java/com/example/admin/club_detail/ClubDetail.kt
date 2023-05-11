package com.example.admin

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.admin.club_detail.ClubDetailRepository
import com.example.admin.club_detail.ClubDetailViewModel
import com.example.admin.dashboard.*
import com.example.common.*
import com.example.common.data.AppState
import com.example.common.data.User

private var clubDetails : Club? = null
@Composable
fun ClubDetail(navController: NavHostController, uuid: String?) {

    val context = LocalContext.current

    val viewModel: ClubDetailViewModel = viewModel(factory = viewModelFactory {
        ClubDetailViewModel(ClubDetailRepository())
    })

    val showProgressBar = remember {
        mutableStateOf(false)
    }
    val club: State<AppState<Club>> = viewModel.clubState.collectAsState()

    LaunchedEffect(key1 = Unit, block = {
        uuid?.let { viewModel.getClubDetails(it) }
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

    Column() {

        if (showProgressBar.value) {
            CustomCircularProgressBar()
        }

        clubDetails?.let {
            Text(text = "Club Name")
            Text(text = it.name)
            Text(text = "Owner Admin")
            Text(text = it.createdBy.fullName)
            Text(text = "Club Address")
            Text(text = it.location)
        }

        DashboardTabView(tabTitles = arrayOf("Members", "Posts", "Requests")) {
            when (it) {
                0 -> clubDetails?.let { it1 -> ClubMembers(members = it1.members) }
                1 -> clubDetails?.let { it1 -> ClubPosts(posts = it1.list) }
                2 -> clubDetails?.let { it1 -> ClubRequests(requests = it1.requests ) }
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
fun ClubRequests(requests: List<Request>) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(requests.size) {
            Column {
                Text(
                    text = requests[it].requestedBy.fullName,
                    modifier = Modifier.padding(10.dp)
                )
                Divider(thickness = 2.dp)
            }

        }
    }
}