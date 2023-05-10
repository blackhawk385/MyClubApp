package com.example.admin.add_post

import android.util.Log
import com.example.common.Club
import com.example.common.Posts
import com.example.common.persistance.FirebaseUtil
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow

class AddPostRepository {

    fun getAllclubSnapShot(): Flow<List<Club>?> {
        return callbackFlow {
            FirebaseUtil.getAllData("clubs", onFailure = {
                Log.d("AddPostRepository", "getAllclubSnapShot: $it")
            }, onSuccess = {
                trySend(FirebaseUtil.createClubData(it))
            })
            awaitClose {}
        }
    }

    fun addPost(post: Posts) =
        FirebaseUtil.addData("Posts", post)
}