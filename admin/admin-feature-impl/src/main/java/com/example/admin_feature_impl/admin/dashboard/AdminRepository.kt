package com.example.admin.dashboard

import com.example.common.Club
import com.example.common.Posts
import com.example.common.data.AppState
import com.example.common.persistance.FirebaseUtil
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class AdminRepository {

    fun getAllClubs(collection: String): Flow<AppState<List<Club>>> {
        return callbackFlow {
            trySend(AppState.Loading())
            FirebaseUtil.getAllDataFromACollection(collection, onSuccess = {
                trySend(AppState.Success(FirebaseUtil.createClubData(it)))
            }, onFailure = {
                trySend(AppState.Error(listOf()))
            })
            awaitClose {}
        }
    }

    fun getAllPosts(collection: String): Flow<List<Posts>> {
        return callbackFlow {
            FirebaseUtil.getAllDataFromACollection(collection, onSuccess = {
                trySend(FirebaseUtil.createPostData(it))
            }, onFailure = {
                trySend(listOf())
            })
            awaitClose {}
        }
    }
}