package com.example.admin.add_post

import androidx.lifecycle.ViewModel
import com.example.common.Club
import com.example.common.Posts
import com.example.common.data.User
import kotlinx.coroutines.flow.Flow

class AddPostViewModel(private val repository: AddPostRepository) : ViewModel() {


    fun getClubs(): Flow<List<Club>?> {
        return repository.getAllclubSnapShot()
    }

    fun addPost(title: String, desc: String, link: String, city: String, club: Club, user: User) =
        repository.addPost(
            Posts(
                title = title,
                description = desc,
                comments = listOf(),
                author = user,
                link = link,
                associateClub = club
            )
        )

}