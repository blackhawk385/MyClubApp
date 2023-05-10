package com.example.member

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.common.ButtonControl
import com.example.common.InputTextField

@Composable
fun AddPost(navController: NavHostController) {
    val titleEditText = rememberSaveable() {
        mutableStateOf("")
    }

    val cityEditText = rememberSaveable() {
        mutableStateOf("")
    }
    val linkEditText = rememberSaveable() {
        mutableStateOf("")
    }
    val descriptionEditText = rememberSaveable() {
        mutableStateOf("")
    }

    val club = rememberSaveable() {
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
            modifier = Modifier.fillMaxWidth(), label = "Title", value = titleEditText.value,
            onValueChangeListner = {
                titleEditText.value = it
            }
        )
        InputTextField(
            modifier = Modifier.fillMaxWidth(), label = "City", value = cityEditText.value,
            onValueChangeListner = {
                cityEditText.value = it
            }
        )

        InputTextField(modifier = Modifier
            .clickable {
//            DatePicker()
            }
            .fillMaxWidth(), label = "Link", value = linkEditText.value, onValueChangeListner = {
            linkEditText.value = it
        })

//        InputTextField(modifier = Modifier.fillMaxWidth(), label = "DOB")   // dob

        //club admin checkbox

        //email feild with validation
        InputTextField(
            modifier = Modifier.fillMaxWidth(),
            label = "Description",
            value = descriptionEditText.value,
            onValueChangeListner = {
                descriptionEditText.value = it
            }
        )
        //password field
        InputTextField(
            modifier = Modifier.fillMaxWidth(),
            label = "Club",
            value = club.value,
            onValueChangeListner = {
                club.value = it
            }
        )

        ButtonControl(
            buttonText = "Post",
            modifier = Modifier
                .align(alignment = Alignment.End)
                .padding(top = 10.dp)
        )

    }

}