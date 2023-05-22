package com.example.member_feature_impl.member.dashboard

import com.example.common.Club
import com.example.common.Posts
import com.example.common.data.AppState
import com.example.common.persistance.FirebaseUtil
import com.example.common.persistance.POST_COLLECTION
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class MemberRepository {
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

    fun getAllPosts(uuid: String): Flow<List<Posts>> {
        return callbackFlow {
            FirebaseUtil.getConditionalListOfDocumentSnapShot(POST_COLLECTION, "author.uuid",uuid, onSuccess = {
                trySend(FirebaseUtil.createPostData(it))
            })
            awaitClose {}
        }
    }
}