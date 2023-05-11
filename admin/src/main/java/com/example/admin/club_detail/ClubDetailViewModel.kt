package com.example.admin.club_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.Club
import com.example.common.data.AppState
import com.example.common.data.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ClubDetailViewModel(val repository: ClubDetailRepository): ViewModel() {

    private val mutableState: MutableStateFlow<AppState<Club>> = MutableStateFlow<AppState<Club>>(
        AppState.Idle())
    val clubState: StateFlow<AppState<Club>> = mutableState.asStateFlow()

    fun getClubDetails(uuid: String){
        viewModelScope.launch {
            repository.getClubDetailsFromFirebase(uuid).collectLatest {
                mutableState.value = it
            }
        }
    }

}