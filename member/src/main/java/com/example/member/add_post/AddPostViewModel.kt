package com.example.member.add_post

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.Club
import com.example.common.Posts
import com.example.common.data.AppState
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class AddPostViewModel(private val repository: AddPostRepository) : ViewModel() {

    private val updateClubMutableState: MutableStateFlow<AppState<Boolean>> = MutableStateFlow(
        AppState.Idle())
    val updateClubState: StateFlow<AppState<Boolean>> = updateClubMutableState.asStateFlow()

    fun getClubs(): Flow<List<Club>?> {
        return repository.getAllClubSnapShot()
    }

    fun getAClubs(uuid: String): Flow<List<Club>?> {
        return repository.getAClub(uuid)
    }

    fun addPost(post: Posts) =
        repository.addPost(
            post
        )

    fun updateClub(uuid: String, post: Posts){
        viewModelScope.launch {
            repository.updateAssocistedClubDocument(uuid, post).collectLatest {
                updateClubMutableState.value = it
            }
        }
    }
}