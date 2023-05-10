package com.example.admin.dashboard

import com.example.common.Club
import com.example.common.Posts
import com.example.common.data.AppState
import com.example.common.data.User
import com.example.common.persistance.FirebaseUtil
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow

class AdminRepository {

    fun getAllClubs(collection: String): Flow<List<Club>> {
        return callbackFlow {
            FirebaseUtil.getAllData(collection, onSuccess = {
                trySend(FirebaseUtil.createClubData(it))
            }, onFailure = {
                trySend(listOf())
            })
            awaitClose {}
        }
    }

    fun getAllPosts(collection: String): Flow<List<Posts>> {
        return callbackFlow {
            FirebaseUtil.getAllData(collection, onSuccess = {
                trySend(FirebaseUtil.createPostData(it))
            }, onFailure = {
                trySend(listOf())
            })
            awaitClose {}
        }
    }
}