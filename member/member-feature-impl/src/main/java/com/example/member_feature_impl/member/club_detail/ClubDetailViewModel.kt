package com.example.member_feature_impl.member.club_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.Club
import com.example.common.Posts
import com.example.common.Request
import com.example.common.data.AppState
import com.example.common.persistance.REQUEST_COLLECTION
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

    private val addRequestMutableState: MutableStateFlow<AppState<Any>> = MutableStateFlow<AppState<Any>>(
        AppState.Idle())
    val addRequestState: StateFlow<AppState<Any>> = addRequestMutableState.asStateFlow()

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

    fun requestToJoinClub(clubUUID: Request){
        viewModelScope.launch {
            repository.addRequest(clubUUID).collectLatest {
                addRequestMutableState.value = it
            }
        }
    }

    fun getClubJoinRequests(uuid: String){
        viewModelScope.launch {
            repository.getClubJoinRequestFromFirebase(REQUEST_COLLECTION, uuid).collectLatest {
                clubRequestsMutableState.value = it
            }
        }

    }
}