package com.example.login.viewmodel

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.data.User
import com.example.common.data.AppState
import com.example.login.repository.IRegisterRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class RegisterViewModel(val repository: IRegisterRepository) : ViewModel() {
    private val mutableState: MutableStateFlow<AppState<User>> = MutableStateFlow(AppState.Idle())
    private val createProfileState: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val state = mutableState.asStateFlow()
    val createProfileStatus = createProfileState.asStateFlow()

    fun onRegisterPressed(email: String, password: String) {
        viewModelScope.launch {
            kotlin.runCatching {
                repository.registerUsingEmailAndPassword(email, password).collectLatest {
                    mutableState.value = it
                }
            }
        }
    }

    fun createProfile(
        fullname: String,
        gender: String,
        isAdmin: Boolean,
        dob: String,
        city: String,
        uuid: String,
        email: String
    ) {
        viewModelScope.launch {
            createProfileState.value = repository.saveUserInfo(
                fullName = fullname,
                gender = gender,
                isAdmin = isAdmin,
                dob = dob,
                city = city,
                uuid = uuid,
                email = email
            )
        }
    }
}