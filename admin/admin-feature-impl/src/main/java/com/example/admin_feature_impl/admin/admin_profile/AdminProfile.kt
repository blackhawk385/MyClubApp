package com.example.admin

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import com.example.admin.admin_profile.AdminProfileRepository
import com.example.admin.admin_profile.AdminProfileViewModel
import com.example.common.*
import com.example.common.data.AppState
import com.example.common.data.User
import com.example.common.persistance.FirebaseUtil
import com.example.common.persistance.SharedPreference
import com.example.common.persistance.USER_COLLECTION
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun AdminProfile(navController: NavHostController) {

    val viewModel: AdminProfileViewModel = viewModel(factory = viewModelFactory {
        AdminProfileViewModel(AdminProfileRepository())
    })

    val updateUserDetail = viewModel.userDetailState.collectAsState()

    val context = LocalContext.current

    var isEnabled by rememberSaveable { mutableStateOf(false) }

    val fullNameEditText = rememberSaveable() {
        mutableStateOf("")
    }

    val uuid = rememberSaveable() {
        mutableStateOf("")
    }

    val cityEditText = rememberSaveable() {
        mutableStateOf("")
    }

    val dob = rememberSaveable() {
        mutableStateOf("")
    }

    val showProgressBar = remember {
        mutableStateOf(true)
    }

    val genderState = remember {
        mutableStateOf("")
    }

    val emailState = remember {
        mutableStateOf("")
    }

    val isAdminState = remember {
        mutableStateOf(false)
    }

    var dropDownExpanded by remember { mutableStateOf(false) }

    val showDialog = remember {
        mutableStateOf(false)
    }

    if (dropDownExpanded) {
        DropDownMenu(expanded = dropDownExpanded, list = listOf("Male", "Female"), onDismiss = {
            dropDownExpanded = false
        }, onClick = {
            genderState.value = it
            dropDownExpanded = false
        })
    }

    LaunchedEffect(Unit) {
        //loggedIn User
        SharedPreference(context).getUser {
            uuid.value = it.uuid
            fullNameEditText.value = it.fullName
            dob.value = it.dob
            cityEditText.value = it.city
            genderState.value = it.gender
            emailState.value = it.email
            isAdminState.value = it.isAdmin
            showProgressBar.value = false
        }
    }

    LaunchedEffect(key1 = updateUserDetail.value, block = {
        when (updateUserDetail.value) {
            is AppState.Error -> showMessage(context, "error while updating user details")
            is AppState.Idle -> {}
            is AppState.Loading -> showProgressBar.value = true
            is AppState.Success -> {
                FirebaseUtil.getSingleDocument(USER_COLLECTION, uuid.value) {
                    CoroutineScope(Dispatchers.IO).launch {
                        SharedPreference(context).setUser(FirebaseUtil.createUserData(it))
                        showProgressBar.value = false
                        withContext(Dispatchers.Main) {
                            navController.navigateUp()
                        }
                    }

                }
            }
        }
    })

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp),
        modifier = Modifier
            .padding(10.dp)
            .verticalScroll(rememberScrollState())

    ) {

        Text(
            text = "Profile",
            modifier = Modifier.padding(top = 100.dp),
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )
        InputTextField(
            modifier = Modifier.fillMaxWidth(),
            label = "Full Name",
            value = fullNameEditText.value,
            onValueChangeListner = {
                fullNameEditText.value = it
            },
            enabled = isEnabled
        )

        Text(text = emailState.value, fontWeight = FontWeight.Bold)
        InputTextField(
            modifier = Modifier.fillMaxWidth(),
            label = "City",
            value = cityEditText.value,
            onValueChangeListner = {
                cityEditText.value = it
            }, enabled = isEnabled
        )

        InputTextField(modifier = Modifier
            .clickable {
                showDialog.value = true
            }
            .fillMaxWidth(),
            label = "DOB",
            value = dob.value,
            onValueChangeListner = {
                dob.value = it
            },
            enabled = isEnabled
        )

        InputTextField(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    dropDownExpanded = true
                },
            label = "Gender",
            value = genderState.value,
            onValueChangeListner = {
                genderState.value = it
            },
            enabled = isEnabled
        )

        if (showDialog.value) {
            DatePicker(mContext = context, mDate = dob.value, onDateSelected = {
                dob.value = it
                showDialog.value = false
            })
        }

        if (showProgressBar.value) {
            CustomCircularProgressBar()
        }

        if (!isEnabled) {
            ButtonControl(
                buttonText = "Update",
                modifier = Modifier
                    .align(alignment = Alignment.End)
                    .padding(top = 10.dp), onClick = {
                    isEnabled = true
                })
        }

        if (isEnabled) {
            ButtonControl(
                buttonText = "Done",
                modifier = Modifier
                    .align(alignment = Alignment.End)
                    .padding(top = 10.dp), onClick = {
                    viewModel.updateUserDetail(
                        User(
                            uuid = uuid.value,
                            fullName = fullNameEditText.value,
                            city = cityEditText.value,
                            dob = dob.value,
                            gender = genderState.value,
                            isAdmin = isAdminState.value,
                            email = emailState.value
                        )
                    )
                })
        }
    }

}

