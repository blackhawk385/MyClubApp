package com.example.admin

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.admin.add_post.AddPostRepository
import com.example.admin.add_post.AddPostViewModel
import com.example.common.*
import com.example.common.data.User
import com.example.common.persistance.FirebaseUtil
import com.example.common.persistance.SharedPreference
import kotlinx.coroutines.flow.collectLatest


private var loggedInUser : User? = null
private var selectedClub: Club = Club()
@Composable
fun AddPost(navController: NavHostController) {

    val context = LocalContext.current
    val viewModel: AddPostViewModel = viewModel(factory = viewModelFactory {
        AddPostViewModel(AddPostRepository())
    })

    val clubData = rememberSaveable() {
        mutableStateOf(listOf<Club>())
    }
    LaunchedEffect(Unit) {

        viewModel.getClubs().collectLatest {
            it?.let {
                clubData.value = it
            }
        }
    }

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

    var expanded by remember { mutableStateOf(false) }

    val showProgressBar = remember {
        mutableStateOf(false)
    }
    LaunchedEffect(Unit) {
        //loggedIn User
        SharedPreference(context).getUser {
            loggedInUser = it
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp),
        modifier = Modifier.padding(10.dp)
    ) {
        if (showProgressBar.value) {
            CustomCircularProgressBar()
        }
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
            .fillMaxWidth(),
            label = "Link",
            value = linkEditText.value,
            onValueChangeListner = {
                linkEditText.value = it
            })

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
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    expanded = true
                },
            label = "Club",
            value = club.value,
            onValueChangeListner = {
                club.value = it

            }, enabled = false
        )

        if (expanded) {
            DropDownMenu(expanded, list = clubData.value, onDismiss = {
                expanded = false
            }, onSelected = {
                expanded = false
                club.value = it
            })
        }
        ButtonControl(
            buttonText = "Post",
            modifier = Modifier
                .align(alignment = Alignment.End)
                .padding(top = 10.dp), onClick = {
                showProgressBar.value = true
                if(!viewModel.addPost(
                    title = titleEditText.value,
                    city = cityEditText.value,
                    desc = descriptionEditText.value,
                    club = selectedClub,
                    link = linkEditText.value,
                     user = loggedInUser!!
                )){
                    showProgressBar.value = false
                    navController.popBackStack()
                }
            })

    }
}

@Composable
fun DropDownMenu(
    expanded: Boolean,
    onDismiss: (Boolean) -> Unit,
    list: List<Club>?,
    onSelected: (String) -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.TopCenter)
    ) {

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                onDismiss(expanded)
            }, modifier = Modifier.fillMaxWidth()
        ) {
            list?.let {
                it.forEach {
                    DropdownMenuItem(
                        onClick = {
                            onSelected(it.name)
                            selectedClub = it
                        }
                    ) {
                        Text(text = it.name)
                    }
                }
            }
        }
    }
}