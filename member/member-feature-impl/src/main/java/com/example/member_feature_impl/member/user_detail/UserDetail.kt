package com.example.member_feature_impl.member.user_detail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.common.*
import com.example.common.data.AppState
import com.example.common.data.User
import com.example.common.persistance.FirebaseUtil
import com.example.common.persistance.SharedPreference
import com.example.common.persistance.USER_COLLECTION
import com.example.member_feature_impl.member.navigation.MemberEnum

private var loggedInUser: User? = null
private var userData: User? = null
private var clubList: List<Club>? = null
private var myPostList: List<Posts>? = null

@Composable
fun UserDetail(navController: NavHostController) {
    val context = LocalContext.current

    val viewModel: UserDetailViewModel = viewModel(factory = viewModelFactory {
        UserDetailViewModel(UserDetailRepository())
    })

    val allPosts = viewModel.myPostState.collectAsState()

    LaunchedEffect(Unit) {
        //loggedIn User
        SharedPreference(context).getUser { user ->
            loggedInUser = user
            FirebaseUtil.getSingleDocument(USER_COLLECTION, user.uuid) {
                userData = FirebaseUtil.createUserData(it)
                viewModel.getMyPostsData(user.uuid)

            }
        }

    }

    LaunchedEffect(allPosts.value) {
        when(allPosts.value){
            is AppState.Error -> ""
            is AppState.Idle -> ""
            is AppState.Loading -> ""
            is AppState.Success -> {
                myPostList = allPosts.value.data
            }
        }
    }

    Column(modifier = Modifier.padding(20.dp)) {

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "User Profile",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(10.dp))

        loggedInUser?.let {
            DetailText(data = it)
        }

        Spacer(modifier = Modifier.height(10.dp))

        DashboardTabView(
            tabTitles = arrayOf("Joined club", "Posts"),
            modifier = Modifier.weight(6f)
        ) {
            when (it) {
                0 -> {
                    clubList?.let {  JoinedClub(navController, it)}
                }
                1 -> {
                    myPostList?.let{PostList(navController, it)}
                }
            }
        }
        Column(modifier = Modifier.weight(1f)) {
            Divider(color = Color.Blue)
        }

        //user profile - if uuid same then show button otherwise
        if (userData?.uuid != loggedInUser?.uuid) {
            ButtonControl(buttonText = "Update My Profile", onClick = {
                navController.navigate(MemberEnum.MemberProfile.name)
            })
        }
    }
}

@Composable
fun PostList(navController: NavController, postList : List<Posts>) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(postList.size) {
            Column {
                Text(
                    text = postList[it].title,
                    modifier = Modifier
                        .padding(10.dp)
                        .clickable {
                            navController.navigate(
                                MemberEnum.PostDetails.name.plus(
                                    "/${
                                        postList.get(
                                            it
                                        ).uuid
                                    }"
                                )
                            )
                        },
                    fontSize = 14.sp
                )
                Divider(thickness = 2.dp)
            }

        }
    }
}

@Composable
fun UpdateProfile() {
    val memberName = rememberSaveable() {
        mutableStateOf("")
    }

    val gender = rememberSaveable() {
        mutableStateOf("")
    }
    val dob = rememberSaveable() {
        mutableStateOf("")
    }
    val city = rememberSaveable() {
        mutableStateOf("")
    }

    val phoneNumber = rememberSaveable() {
        mutableStateOf("")
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp),
        modifier = Modifier.padding(10.dp)
    ) {
        Text(
            text = "New Post",
            modifier = Modifier.padding(top = 100.dp),
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )
        InputTextField(
            modifier = Modifier.fillMaxWidth(), label = "Member Name", value = memberName.value,
            onValueChangeListner = {
                memberName.value = it
            }
        )
        InputTextField(
            modifier = Modifier.fillMaxWidth(), label = "Gender", value = gender.value,
            onValueChangeListner = {
                gender.value = it
            }
        )

        InputTextField(modifier = Modifier
            .clickable {
//            DatePicker()
            }
            .fillMaxWidth(), label = "DOB", value = dob.value, onValueChangeListner = {
            dob.value = it
        })

        InputTextField(
            modifier = Modifier.fillMaxWidth(),
            label = "City",
            value = city.value,
            onValueChangeListner = {
                city.value = it
            }
        )
        InputTextField(
            modifier = Modifier.fillMaxWidth(),
            label = "Phone Number",
            value = phoneNumber.value,
            onValueChangeListner = {
                phoneNumber.value = it
            }
        )

        ButtonControl(
            buttonText = "Add",
            modifier = Modifier
                .align(alignment = Alignment.End)
                .padding(top = 10.dp)
        )

    }
}

@Composable
fun JoinedClub(navController: NavHostController, joinedClubList: List<Club>) {

    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(16.dp)
    ) {
        joinedClubList.size.let {
            items(it) {
                Column {
                    Text(
                        text = joinedClubList[it].name,
                        modifier = Modifier
                            .padding(10.dp)
                            .clickable {
                                navController.navigate(
                                    MemberEnum.ClubDetails.name.plus(
                                        "/${
                                            joinedClubList.get(
                                                it
                                            ).uuid
                                        }"
                                    )
                                )
                            }, fontWeight = FontWeight.Bold
                    )
                    Divider(thickness = 2.dp)
                }
            }
        }

    }
}