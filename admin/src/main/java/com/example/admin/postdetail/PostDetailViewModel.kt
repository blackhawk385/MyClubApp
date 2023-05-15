package com.example.admin.postdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.Comments
import com.example.common.Posts
import com.example.common.data.AppState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class PostDetailViewModel(private val repository: PostDetailRepopsitory) : ViewModel() {

    private val mutableState: MutableStateFlow<AppState<Posts>> = MutableStateFlow<AppState<Posts>>(
        AppState.Idle()
    )
    val postState: StateFlow<AppState<Posts>> = mutableState.asStateFlow()

    private val commentsMutableState: MutableStateFlow<AppState<Boolean>> = MutableStateFlow<AppState<Boolean>>(
        AppState.Idle()
    )
    val commentsState: StateFlow<AppState<Boolean>> = commentsMutableState.asStateFlow()

    private val getCommentsMutableState: MutableStateFlow<AppState<List<Comments>>> =
        MutableStateFlow<AppState<List<Comments>>>(AppState.Idle())
    val getCommentsState: StateFlow<AppState<List<Comments>>> =
        getCommentsMutableState.asStateFlow()

    fun getPostDetails(uuid: String) {
        viewModelScope.launch {
            repository.getPostDetailsFromFirebase(uuid).collectLatest {
                mutableState.value = it
            }
        }
    }

    fun getComments(uuid: String) {
        viewModelScope.launch {
            repository.getCommentsFromFirebase(uuid).collectLatest {
                getCommentsMutableState.value = it
            }
        }
    }

    fun addComments(comments: Comments) {
        viewModelScope.launch {
            repository.addCommentOnPost(comments).collectLatest {
                commentsMutableState.value = it
            }
        }
    }
}