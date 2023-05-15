package com.example.member.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.Club
import com.example.common.Posts
import com.example.common.data.AppState
import com.example.common.persistance.CLUB_COLLECTION
import com.example.common.persistance.POST_COLLECTION
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MemberDashboardViewModel(val repository: MemberRepository) : ViewModel(){

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

    fun getMyPosts(uuid: String){
        viewModelScope.launch {
            repository.getAllPosts(uuid).collectLatest {
                postMutableState.value = it
            }
        }
    }
}