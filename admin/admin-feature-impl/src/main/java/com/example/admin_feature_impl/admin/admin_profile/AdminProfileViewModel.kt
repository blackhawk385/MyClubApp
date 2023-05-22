package com.example.admin.admin_profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.data.AppState
import com.example.common.data.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class AdminProfileViewModel(private val repository: AdminProfileRepository): ViewModel() {
    private val userDetailMutableState: MutableStateFlow<AppState<String>> = MutableStateFlow<AppState<String>>(
        AppState.Idle())
    val userDetailState: StateFlow<AppState<String>> = userDetailMutableState.asStateFlow()

    fun updateUserDetail(user: User) {
        viewModelScope.launch {
            repository.updateUserDetails(user).collectLatest {
                userDetailMutableState.value = it
            }
        }

    }
}