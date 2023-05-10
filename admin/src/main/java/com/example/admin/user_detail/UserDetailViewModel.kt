package com.example.admin.user_detail

import androidx.lifecycle.ViewModel
import com.example.common.data.AppState
import com.example.common.data.User
import com.example.common.persistance.FirebaseUtil
import com.example.common.persistance.SharedPreference
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class UserDetailViewModel : ViewModel() {

    private val userState: MutableStateFlow<User> = MutableStateFlow<User>(User())
    val state: StateFlow<User> = userState.asStateFlow()

    fun getLoggedInUser() {

    }
}