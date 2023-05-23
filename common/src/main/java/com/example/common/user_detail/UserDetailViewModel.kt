package com.example.admin.user_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.Posts
import com.example.common.data.AppState
import com.example.common.data.User
import com.example.common.persistance.FirebaseUtil
import com.example.common.persistance.SharedPreference
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class UserDetailViewModel(private val repository: UserDetailRepository) : ViewModel() {

    private val myPostMutableState: MutableStateFlow<AppState<List<Posts>>> = MutableStateFlow<AppState<List<Posts>>>(AppState.Idle())
    val myPostState: StateFlow<AppState<List<Posts>>> = myPostMutableState.asStateFlow()

    fun getJoinedClubData() {

    }

    fun getMyPostsData(uuid: String) {
        viewModelScope.launch {
            repository.getMyPostsFromFirebase(uuid).collectLatest {
                myPostMutableState.value = it
            }
        }

    }
}