package com.example.login

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.common.ButtonControl
import com.example.common.InputTextField
import com.example.common.data.AppState
import com.example.common.data.User
import com.example.common.showMessage
import com.example.common.viewModelFactory
import com.example.login.repository.LoginRepository
import com.example.login.ui.theme.MyClubAppTheme
import com.example.login.viewmodel.LoginViewModel
import java.util.regex.Pattern


val EMAIL_ADDRESS_PATTERN = Pattern.compile(
    "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
            "\\@" +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
            "(" +
            "\\." +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
            ")+"
)

fun isValidEmailAddress(email: String): Boolean = EMAIL_ADDRESS_PATTERN.matcher(email).matches()
fun passwordValidator(password: String) = password.length > 5

@Composable
fun LoginScreen(navController: NavController) {

    val context = LocalContext.current

    val viewModel: LoginViewModel = viewModel(factory = viewModelFactory {
        LoginViewModel(LoginRepository())
    })

    val emailEditText = rememberSaveable() {
        mutableStateOf("")
    }

    val showProgressBar = remember {
        mutableStateOf(false)
    }

    val passwordEditText = rememberSaveable() {
        mutableStateOf("")
    }

    val user: AppState<User> = viewModel.state.collectAsStateWithLifecycle().value
    LaunchedEffect(viewModel.state.value) {
        when (user) {
            is AppState.Error -> showMessage(context, "Error while login")
            is AppState.Loading -> showProgressBar.value = true
            is AppState.Success<User> -> {
                viewModel.getUserFromFirebase(user.response.uuid, context = context)
                showProgressBar.value = false
                //navigate to home
            }
            is AppState.Idle -> {
                Log.d("LoginViewModel", "Idle")
            }
            else -> {
                Log.d("LoginViewModel", "null")
            }
        }
    }


    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp), modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        if (showProgressBar.value) {
            CustomCircularProgressBar()
        }
        Text(
            text = "Welcome to MyClubApp",
            modifier = Modifier.padding(top = 100.dp),
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )
        //state hoisted
        InputTextField(modifier = Modifier.fillMaxWidth(), value = emailEditText.value) {
            emailEditText.value = it
        }
        InputTextField(modifier = Modifier.fillMaxWidth(), value = passwordEditText.value) {
            passwordEditText.value = it
        }
        ButtonControl(
            buttonText = stringResource(R.string.label_btn_login),
            modifier = Modifier
                .align(alignment = Alignment.End)
                .padding(top = 10.dp), enabled = isValidEmailAddress(emailEditText.value)
                    && passwordValidator(passwordEditText.value), onClick = {
                viewModel.onLoginPressed(emailEditText.value, passwordEditText.value)
            }
        )

        Column {
            Text(text = "Register:", fontSize = 16.sp, fontWeight = FontWeight.Bold)

            ButtonControl(
                buttonText = stringResource(R.string.label_btn_email),
                modifier = Modifier
                    .align(alignment = Alignment.Start)
                    .padding(start = 70.dp), onClick = {
                    navController.navigate(LoginEnum.RegisterScreen.name)
                }
            )

            ButtonControl(
                buttonText = stringResource(R.string.label_btn_google_login),
                modifier = Modifier
                    .align(alignment = Alignment.Start)
                    .padding(start = 70.dp), enabled = false
            )

            ButtonControl(
                buttonText = stringResource(R.string.label_btn_fb_login),
                modifier = Modifier
                    .align(alignment = Alignment.Start)
                    .padding(start = 70.dp), enabled = false
            )
        }

    }
}

//@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyClubAppTheme {
//        LoginScreen()
    }
}