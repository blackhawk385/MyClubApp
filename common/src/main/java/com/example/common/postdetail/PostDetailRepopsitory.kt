package com.example.admin.postdetail

import com.example.common.Comments
import com.example.common.Posts
import com.example.common.data.AppState
import com.example.common.persistance.COMMENTS_COLLECTION
import com.example.common.persistance.FirebaseUtil
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

const val POST_COLLECTION = "posts"

class PostDetailRepopsitory {

    fun getPostDetailsFromFirebase(uuid: String): Flow<AppState<Posts>> {
        return callbackFlow<AppState<Posts>> {
            trySend(AppState.Loading())
            FirebaseUtil.getSingleDocument(POST_COLLECTION, uuid) {
                if (it.size > 0) {
                    trySend(AppState.Success(FirebaseUtil.createPostData(it)))
                } else {
                    trySend(AppState.Error(error("No data found")))
                }
            }

            awaitClose { }
        }
    }

    fun getCommentsFromFirebase(uuid: String): Flow<AppState<List<Comments>>> {
        return callbackFlow<AppState<List<Comments>>> {
            trySend(AppState.Loading())
            FirebaseUtil.getConditionalListOfDocumentSnapShot(COMMENTS_COLLECTION, "associatedPostUUID",uuid) {
//                if (it.size > 0) {
                    trySend(AppState.Success(FirebaseUtil.createCommentData(it)))
//                } else {
//                    trySend(AppState.Error(error("No data found")))
//                }
            }

            awaitClose { }
        }
    }

    fun addCommentOnPost(comment: Comments) : Flow<AppState<Boolean>>{
        return callbackFlow<AppState<Boolean>> {
            trySend(AppState.Loading())
            if(!FirebaseUtil.addData(COMMENTS_COLLECTION, comment)){
                trySend(AppState.Success(true))
            }
            awaitClose { }
        }
    }
}