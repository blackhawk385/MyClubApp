package com.example.admin.postdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.Posts
import com.example.common.data.AppState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class PostDetailViewModel(val repository: PostDetailRepopsitory) : ViewModel() {

    private val mutableState: MutableStateFlow<AppState<Posts>> = MutableStateFlow<AppState<Posts>>(
        AppState.Idle())
    val postState: StateFlow<AppState<Posts>> = mutableState.asStateFlow()

    fun getPostDetails(uuid: String){
        viewModelScope.launch {
            repository.getPostDetailsFromFirebase(uuid).collectLatest {
                mutableState.value = it
            }
        }
    }
}