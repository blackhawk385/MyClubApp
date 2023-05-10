package com.example.member.user_detail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.navigation.NavHostController
import com.example.member.MemberEnum
import com.example.common.ButtonControl
import com.example.common.DashboardTabView
import com.example.common.DetailText
import com.example.common.InputTextField
import com.example.common.data.User
import com.example.common.persistance.FirebaseUtil
import com.example.common.persistance.SharedPreference

@Composable
fun UserDetail(navController: NavHostController) {
    val context = LocalContext.current
    var loggedInUser : User? = null
    var userData : User? = null

    //uuid from nav param

    LaunchedEffect(Unit) {
        //loggedIn User
        SharedPreference(context).getUser() {
            loggedInUser = it
//            FirebaseUtil.getSingleDocument("user", ""){
//               userData = FirebaseUtil.createUserData("", it)
//            }
        }


    }

    Column {
        loggedInUser?.let {
            DetailText(data = it)
        }

        DashboardTabView(tabTitles = arrayOf("Joined club", "Posts")) {

        }
        Column(modifier = Modifier.weight(1f)) {
            Divider(color = Color.Blue)
        }

        //if user is my club member
        ButtonControl(buttonText = "Remove From MyClub", onClick = {
//            navController.navigate(AdminEnum.AdminProfile.name)
        })

        //user profile - if uuid same then show button otherwise
//        if(userData?.uuid != loggedInUser?.uuid) {
        ButtonControl(buttonText = "Update My Profile", onClick = {
            navController.navigate(MemberEnum.MemberProfile.name)
        })
//        }
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