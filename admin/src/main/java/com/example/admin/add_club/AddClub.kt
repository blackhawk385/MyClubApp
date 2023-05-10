package com.example.admin

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
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
import com.example.admin.add_club.AddClubRepository
import com.example.admin.add_club.AddClubViewModel
import com.example.common.*
import com.example.common.data.AppState
import com.example.common.data.User
import com.example.common.persistance.SharedPreference
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

private var user: User? = null

@Composable
fun AddClub(navController: NavHostController) {

    val viewModel: AddClubViewModel = viewModel(factory = viewModelFactory {
        AddClubViewModel(AddClubRepository())
    })
    val context = LocalContext.current

    val clubNameEditText = rememberSaveable() {
        mutableStateOf("")
    }

    val addClubState = remember {
        mutableStateOf(false)
    }

    val ownerAdminEditText = rememberSaveable() {
        mutableStateOf("")
    }
    val noOfRoomsEditText = rememberSaveable() {
        mutableStateOf("")
    }
    val clubAddressEditText = rememberSaveable() {
        mutableStateOf("")
    }

    val showProgressBar = remember {
        mutableStateOf(false)
    }
    val appState = viewModel.state.collectAsState()

    when (appState.value) {
        is AppState.Error -> {
            showProgressBar.value = false
            showMessage(context, "Error while adding club")
        }
        is AppState.Idle -> ""
        is AppState.Loading -> showProgressBar.value = true
        is AppState.Success -> {
            showProgressBar.value = false
            navController.navigate(AdminEnum.AdminDashboardScreen.name)
        }
    }

    LaunchedEffect(Unit) {
        SharedPreference(context).getUser {
            user = it
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
            text = "New Club",
            modifier = Modifier.padding(top = 100.dp),
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )
        InputTextField(
            modifier = Modifier.fillMaxWidth(), label = "Club Name", value = clubNameEditText.value,
            onValueChangeListner = {
                clubNameEditText.value = it
            }
        )
        InputTextField(
            modifier = Modifier.fillMaxWidth(),
            label = "Owner Admin",
            value = "Self",
            onValueChangeListner = {
                ownerAdminEditText.value = it
            },
            enabled = false
        )

        InputTextField(Modifier
            .fillMaxWidth(),
            label = "No. Of Rooms",
            value = noOfRoomsEditText.value,
            onValueChangeListner = {
                noOfRoomsEditText.value = it
            })

        InputTextField(
            modifier = Modifier.fillMaxWidth(),
            label = "Club Address",
            value = clubAddressEditText.value,
            onValueChangeListner = {
                clubAddressEditText.value = it
            }
        )

        ButtonControl(
            buttonText = "Add",
            modifier = Modifier
                .align(alignment = Alignment.End)
                .padding(top = 10.dp), onClick = {
                viewModel.addClub(
                    Club(
                        name = clubNameEditText.value,
                        location = clubAddressEditText.value,
                        createdBy = user!!
                    )
                )
            }
        )

    }
}
