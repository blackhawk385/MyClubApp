package com.example.member_feature_impl.member.add_post

import android.util.Log
import com.example.common.Club
import com.example.common.Posts
import com.example.common.data.AppState
import com.example.common.persistance.CLUB_COLLECTION
import com.example.common.persistance.FirebaseUtil
import com.example.common.persistance.POST_COLLECTION
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class AddPostRepository {
    fun getAllClubSnapShot(): Flow<List<Club>?> {
        return callbackFlow {
            FirebaseUtil.getAllDataFromACollection(CLUB_COLLECTION, onFailure = {
                Log.d("AddPostRepository", "getAllclubSnapShot: $it")
            }, onSuccess = {
                trySend(FirebaseUtil.createClubData(it))
            })
            awaitClose {}
        }
    }
    fun getAClub(uuid: String): Flow<List<Club>?> {
        return callbackFlow {
            FirebaseUtil.getSingleDocument(CLUB_COLLECTION, uuid = uuid, onSuccess = {
                trySend(listOf(FirebaseUtil.createClubData(it)))
            })
            awaitClose {}
        }
    }

    fun addPost(post: Posts) =
        FirebaseUtil.addData(POST_COLLECTION, post)

    fun updateAssocistedClubDocument(uuid: String, post: Posts): Flow<AppState<Boolean>> {
        return callbackFlow<AppState<Boolean>> {
            FirebaseUtil.updateClubWithPost(uuid, post) {
                trySend(AppState.Success(it))
            }
            awaitClose {}
        }

    }
}