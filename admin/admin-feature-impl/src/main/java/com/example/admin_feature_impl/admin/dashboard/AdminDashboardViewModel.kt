package com.example.admin.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.admin.add_club.CLUB_COLLECTION
import com.example.admin.postdetail.POST_COLLECTION
import com.example.common.Club
import com.example.common.Posts
import com.example.common.data.AppState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class AdminDashboardViewModel(private val repository: AdminRepository) : ViewModel() {

    private val mutableState: MutableStateFlow<AppState<List<Club>>> =
        MutableStateFlow(AppState.Idle())
    val state: StateFlow<AppState<List<Club>>> = mutableState.asStateFlow()

    private val postMutableState: MutableStateFlow<List<Posts>> =
        MutableStateFlow(listOf())
    val postState: StateFlow<List<Posts>> = postMutableState.asStateFlow()

    fun getMyClubs(){
        viewModelScope.launch {
            repository.getAllClubs(CLUB_COLLECTION).collectLatest {
                mutableState.value = it
            }
        }
    }

    fun getMyPosts(){
        viewModelScope.launch {
            repository.getAllPosts(POST_COLLECTION).collectLatest {
                postMutableState.value = it
            }
        }
    }

//    fun getMyClubRequests(){
//
//    }
//
//    fun getAllClubs(){
//
//    }
//
//    fun getClubPosts(){
//
//    }
}