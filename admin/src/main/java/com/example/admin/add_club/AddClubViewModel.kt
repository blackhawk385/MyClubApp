package com.example.admin.add_club

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.Club
import com.example.common.data.AppState
import com.example.common.data.User
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class AddClubViewModel(val repository: AddClubContract) : ViewModel() {

    private val mutableState: MutableStateFlow<AppState<String>> =
        MutableStateFlow(AppState.Idle())
    val state: StateFlow<AppState<String>> = mutableState.asStateFlow()


    fun addClub(club: Club) {
        viewModelScope.launch {
            repository.addNewClub(club).collectLatest {
                mutableState.value = it
            }
        }
    }

}