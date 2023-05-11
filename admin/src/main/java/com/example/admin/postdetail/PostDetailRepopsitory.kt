package com.example.admin.postdetail

import com.example.admin.add_club.ADD_CLUB_COLLECTION
import com.example.common.Club
import com.example.common.Posts
import com.example.common.data.AppState
import com.example.common.persistance.FirebaseUtil
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

const val POST_COLLECTION = "Posts"
class PostDetailRepopsitory {

    fun getPostDetailsFromFirebase(uuid: String): Flow<AppState<Posts>>{
            return callbackFlow<AppState<Posts>> {
                trySend(AppState.Loading())
                FirebaseUtil.getSingleDocument(POST_COLLECTION, uuid) {
                    if(it.size > 0) {
                        trySend(AppState.Success(FirebaseUtil.createPostData(it)))
                    }else {
                        trySend(AppState.Error(error("No data found")))
                    }
                }

                awaitClose { }
            }
        }
}