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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.admin.club_detail.ClubDetailRepository
import com.example.admin.club_detail.ClubDetailViewModel
import com.example.admin.postdetail.PostDetailRepopsitory
import com.example.admin.postdetail.PostDetailViewModel
import com.example.common.*
import com.example.common.data.AppState
import com.example.common.data.User

private var postDetails : Posts? = null

@Composable
fun PostDetails(navController: NavHostController, uuid: String?) {

    val context = LocalContext.current

    val viewModel: PostDetailViewModel = viewModel(factory = viewModelFactory {
        PostDetailViewModel(PostDetailRepopsitory())
    })

    val showProgressBar = remember {
        mutableStateOf(false)
    }
    val post: State<AppState<Posts>> = viewModel.postState.collectAsState()

    LaunchedEffect(key1 = Unit, block = {
        uuid?.let { viewModel.getPostDetails(it) }
    })

    LaunchedEffect(key1 = post.value, block = {
        when (viewModel.postState.value) {
            is AppState.Error -> showMessage(context, "Error while loading club details")
            is AppState.Idle -> {}
            is AppState.Loading -> showProgressBar.value = true
            is AppState.Success -> {
                showProgressBar.value = false
                postDetails = viewModel.postState.value.data
            }
        }
    })

    Column() {

        if (showProgressBar.value) {
            CustomCircularProgressBar()
        }

        postDetails?.let {
            Text(text = "Post Title")
            Text(text = it.title)
            Text(text = "Authored By")
            Text(text = it.author.fullName)
            Text(text = "Club")
            Text(text = it.associateClub.name)
            Text(text = "City")
            Text(text = it.associateClub.location)
            Text(text = "Description")
            Text(text = it.description)
        }

        DashboardTabView(tabTitles = arrayOf("Members", "Posts", "Requests"), modifier = Modifier.weight(5f)) {
            when (it) {
                0 -> postDetails?.let { it1 -> PostComment(it1.comments) }
            }
        }
        Column(modifier = Modifier.weight(1f)) {
            Divider(color = Color.Blue)
        }

        ButtonControl(buttonText = "Delete Post if authored")

    }
}

@Composable
fun PostComment(comments: List<Comments>) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(comments.size) {
            Column {
                Text(
                    text = comments[it].comment,
                    modifier = Modifier.padding(10.dp)
                )
                Divider(thickness = 2.dp)
            }

        }
    }
}