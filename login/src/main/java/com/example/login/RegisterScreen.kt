package com.example.login

import android.app.DatePickerDialog
import android.content.Context
import android.util.Log
import android.widget.DatePicker
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.common.*
import com.example.common.data.AppState
import com.example.common.data.User
import com.example.common.persistance.SharedPreference
import com.example.login.repository.RegisterRepository
import com.example.login.viewmodel.RegisterViewModel
import java.util.*

@Composable
fun RegisterScreen(navController: NavController) {

    val viewModel: RegisterViewModel = viewModel(factory = viewModelFactory {
        RegisterViewModel(RegisterRepository())
    })

    val context = LocalContext.current

    val registerUserState: AppState<User> = viewModel.state.collectAsStateWithLifecycle().value

    //could move to viewmodel
    val pref = SharedPreference(context)

    val fullNameEditText = rememberSaveable() {
        mutableStateOf("")
    }

    val cityEditText = rememberSaveable() {
        mutableStateOf("")
    }
    val emailEditText = rememberSaveable() {
        mutableStateOf("")
    }
    val passwordEditText = rememberSaveable() {
        mutableStateOf("")
    }

    val dob = rememberSaveable() {
        mutableStateOf("")
    }

    val checkedState = remember { mutableStateOf(false) }

    val showDialog = remember {
        mutableStateOf(false)
    }
    val showProgressBar = remember {
        mutableStateOf(false)
    }

    val genderState = remember {
        mutableStateOf("")
    }

    var dropDownExpanded by remember { mutableStateOf(false) }

    if (dropDownExpanded) {
        DropDownMenu(expanded = dropDownExpanded, list = listOf("Male", "Female"), onDismiss = {
            dropDownExpanded = false
        }, onClick = {
            genderState.value = it
            dropDownExpanded = false
        })
    }

    LaunchedEffect(viewModel.state.value) {

        when (registerUserState) {
            is AppState.Error -> {
                showMessage(context, registerUserState.message.toString())
            }
            is AppState.Loading -> {
                showProgressBar.value = true
            }
            is AppState.Success -> {
                viewModel.createProfile(
                    fullname = fullNameEditText.value,
                    gender = "Female",
                    city = cityEditText.value,
                    dob = dob.value,
                    isAdmin = checkedState.value,
                    uuid = registerUserState.response.uuid,
                    email = registerUserState.response.email
                )
                pref.setUser(
                    User(
                        fullName = fullNameEditText.value,
                        gender = genderState.value,
                        city = cityEditText.value,
                        dob = dob.value,
                        isAdmin = checkedState.value,
                        uuid = registerUserState.response.uuid,
                        email = registerUserState.response.email
                    )
                )
                showProgressBar.value = false
                navController.popBackStack()
            }
            is AppState.Idle -> {
                Log.d("RegisterViewModel", "Idle")
            }
            else -> {
                Log.d("RegisterViewModel", "null")
            }
        }

    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp),
        modifier = Modifier.padding(10.dp).verticalScroll(rememberScrollState())

    ) {

        Text(
            text = stringResource(R.string.title_register),
            modifier = Modifier.padding(top = 100.dp),
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )
        InputTextField(
            modifier = Modifier.fillMaxWidth(),
            label = stringResource(R.string.full_name),
            value = fullNameEditText.value,
            onValueChangeListner = {
                fullNameEditText.value = it
            }
        )
        InputTextField(
            modifier = Modifier.fillMaxWidth(),
            label = stringResource(R.string.city),
            value = cityEditText.value,
            onValueChangeListner = {
                cityEditText.value = it
            }
        )

        InputTextField(modifier = Modifier
            .clickable {
                showDialog.value = true
            }
            .fillMaxWidth(),
            label = stringResource(R.string.dob),
            value = dob.value,
            onValueChangeListner = {
                dob.value = it
            },
            enabled = false
        )

        InputTextField(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    dropDownExpanded = true
                },
            label = stringResource(R.string.gender),
            value = genderState.value,
            onValueChangeListner = {
                genderState.value = it
            },
            enabled = false
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

        //club admin checkbox
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Checkbox(
                checked = checkedState.value,
                onCheckedChange = { checkedState.value = it },
                colors = CheckboxDefaults.colors(Color.Blue)
            )

            Text(
                text = stringResource(R.string.cb_club_admin),
                modifier = Modifier.align(Alignment.CenterVertically),
                fontWeight = FontWeight.Bold
            )
        }

        //email feild with validation
        InputTextField(
            modifier = Modifier.fillMaxWidth(),
            label = stringResource(R.string.email),
            value = emailEditText.value,
            onValueChangeListner = {
                emailEditText.value = it
            }
        )
        //password field
        InputTextField(
            modifier = Modifier.fillMaxWidth(),
            label = stringResource(R.string.password),
            value = passwordEditText.value,
            onValueChangeListner = {
                passwordEditText.value = it
            }
        )

        ButtonControl(
            buttonText = stringResource(R.string.label_btn_register),
            modifier = Modifier
                .align(alignment = Alignment.End)
                .padding(top = 10.dp), onClick = {
                viewModel.onRegisterPressed(emailEditText.value, passwordEditText.value)
            })

    }

}

@Composable
fun CustomCircularProgressBar() {
    Box(contentAlignment = Alignment.Center) {
        CircularProgressIndicator(
            modifier = Modifier.size(100.dp),
            color = Color.Green,
            strokeWidth = 10.dp
        )
    }
}





