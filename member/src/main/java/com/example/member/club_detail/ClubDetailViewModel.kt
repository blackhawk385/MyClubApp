package com.example.member.club_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.Club
import com.example.common.Posts
import com.example.common.Request
import com.example.common.data.AppState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ClubDetailViewModel(private val repository: ClubDetailRepository) : ViewModel() {
    private val mutableState: MutableStateFlow<AppState<Club>> = MutableStateFlow<AppState<Club>>(
        AppState.Idle())
    val clubState: StateFlow<AppState<Club>> = mutableState.asStateFlow()

    private val clubPostMutableState: MutableStateFlow<AppState<List<Posts>>> = MutableStateFlow<AppState<List<Posts>>>(
        AppState.Idle())
    val postState: StateFlow<AppState<List<Posts>>> = clubPostMutableState.asStateFlow()

    private val clubRequestsMutableState: MutableStateFlow<AppState<List<Request>>> = MutableStateFlow<AppState<List<Request>>>(
        AppState.Idle())
    val requestsState: StateFlow<AppState<List<Request>>> = clubRequestsMutableState.asStateFlow()

    fun getClubDetails(uuid: String){
        viewModelScope.launch {
            repository.getClubDetailsFromFirebase(uuid).collectLatest {
                mutableState.value = it
            }
        }
    }

    fun getClubPost(clubUUID: String){
        viewModelScope.launch {
            repository.getPostsFromFirebase(clubUUID).collectLatest {
                clubPostMutableState.value = it
            }
        }
    }
}