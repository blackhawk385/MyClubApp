package com.example.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.admin.postdetail.PostDetailRepopsitory
import com.example.admin.postdetail.PostDetailViewModel
import com.example.common.*
import com.example.common.data.AppState
import com.example.common.data.User
import com.example.common.persistance.SharedPreference
import java.sql.Time
import java.time.LocalDateTime

private var postDetails: Posts? = null
private var loggedInUser: User? = null


@Composable
fun PostDetails(navController: NavHostController, uuid: String?) {

    val context = LocalContext.current

    val viewModel: PostDetailViewModel = viewModel(factory = viewModelFactory {
        PostDetailViewModel(PostDetailRepopsitory())
    })

    val showProgressBar = remember {
        mutableStateOf(false)
    }

    val showAddCommentProgressBar = remember {
        mutableStateOf(false)
    }

    val addComments = rememberSaveable() {
        mutableStateOf("")
    }
    val commentList = remember { mutableStateListOf<Comments>()}

    val post: State<AppState<Posts>> = viewModel.postState.collectAsState()
    val comments: State<AppState<List<Comments>>> = viewModel.getCommentsState.collectAsState()
    val addCommentsState: State<AppState<Boolean>> = viewModel.commentsState.collectAsState()

    LaunchedEffect(key1 = Unit, block = {
        uuid?.let {
            viewModel.getPostDetails(it)
            viewModel.getComments(it)
        }
        SharedPreference(context).getUser {
            loggedInUser = it
        }
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

    LaunchedEffect(key1 = addCommentsState.value, block = {
        when (addCommentsState.value) {
            is AppState.Error -> showMessage(context, "Error while adding comment")
            is AppState.Idle -> {}
            is AppState.Loading -> showAddCommentProgressBar.value = true
            is AppState.Success -> {
                showAddCommentProgressBar.value = false
                addComments.value = ""
                showMessage(context, "Comment added successfully")
                uuid?.let { viewModel.getComments(it) }
            }
        }
    })

    LaunchedEffect(key1 = comments.value, block = {
        when (comments.value) {
            is AppState.Error -> showMessage(context, "Error while loading comments")
            is AppState.Idle -> {}
            is AppState.Loading -> {
                showProgressBar.value = true
            }
            is AppState.Success -> {
                showProgressBar.value = false
                viewModel.getCommentsState.value.data?.let { commentList.addAll(it) }
            }
        }
    })

    Column(modifier = Modifier.padding(20.dp)) {

        if (showProgressBar.value) {
            CustomCircularProgressBar()
        }
        Text(
            text = "Post Details",
            modifier = Modifier
                .padding(top = 10.dp)
                .align(Alignment.CenterHorizontally),
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
        Spacer(modifier = Modifier.height(10.dp))

        postDetails?.let {

            Row {
                Text(
                    text = "Post Title: ",
                    fontSize = 20.sp,
                    modifier = Modifier.alignByBaseline()
                )

                Text(
                    text = it.title,
                    fontSize = 12.sp,
                    modifier = Modifier.alignByBaseline(),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Row {
                Text(
                    text = "Authored By: ",
                    fontSize = 20.sp,
                    modifier = Modifier.alignByBaseline()
                )

                Text(
                    text = it.author.fullName,
                    fontSize = 12.sp,
                    modifier = Modifier.alignByBaseline(),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Row {
                Text(
                    text = "Club: ",
                    fontSize = 20.sp,
                    modifier = Modifier.alignByBaseline()
                )

                Text(
                    text = it.associateClub.name,
                    fontSize = 12.sp,
                    modifier = Modifier.alignByBaseline(),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Row {
                Text(
                    text = "City: ",
                    fontSize = 20.sp,
                    modifier = Modifier.alignByBaseline()
                )

                Text(
                    text = it.associateClub.location,
                    fontSize = 12.sp,
                    modifier = Modifier.alignByBaseline(),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Row {
                Text(
                    text = "Description: ",
                    fontSize = 20.sp,
                    modifier = Modifier.alignByBaseline()
                )

                Text(
                    text = it.description,
                    fontSize = 12.sp,
                    modifier = Modifier.alignByBaseline(),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

        DashboardTabView(
            tabTitles = arrayOf("Comments"), modifier = Modifier
                .weight(3f)
        ) {
            when (it) {
                0 -> commentList?.let { it1 ->
                    PostComment(it1)
                }
            }
        }

        Spacer(Modifier.height(10.dp))
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
        ) {
            Divider(color = Color.Blue, modifier = Modifier.background(Color.Yellow))
            InputTextField(
                modifier = Modifier.fillMaxWidth(),
                value = addComments.value,
                onValueChangeListner = {
                    addComments.value = it
                }, label = "Add comment"
            )

            Row {
                ButtonControl(
                    buttonText = "Add comment", onClick = {
                        loggedInUser?.let { user ->
                            postDetails?.uuid?.let { postUUID ->
                                viewModel.addComments(
                                    Comments(
                                        addComments.value,
                                        authoredOn = LocalDateTime.now().toString(),
                                        author = user,
                                        associatedPostUUID = uuid!!
                                    )
                                )
                            }
                        }
                    }
                )

                Spacer(modifier = Modifier.width(10.dp))

                if (showAddCommentProgressBar.value) {
                    CustomCircularProgressBar(
                        Modifier
                            .size(40.dp)
                            .align(Alignment.CenterVertically)
                    )
                }


            }

        }



        if (loggedInUser?.uuid == postDetails?.author?.uuid) {
            ButtonControl(
                buttonText = "Delete Post",
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
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