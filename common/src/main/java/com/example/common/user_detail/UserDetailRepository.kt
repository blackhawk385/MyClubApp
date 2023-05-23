package com.example.admin.user_detail

import com.example.admin.postdetail.POST_COLLECTION
import com.example.common.Posts
import com.example.common.data.AppState
import com.example.common.persistance.FirebaseUtil
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class UserDetailRepository {


    fun getJoinedClubFromFirebase(){
    }
    fun getMyPostsFromFirebase(uuid: String): Flow<AppState<List<Posts>>>{

        return callbackFlow<AppState<List<Posts>>> {
            FirebaseUtil.getConditionalListOfDocumentSnapShot(POST_COLLECTION, "author.uuid",uuid){
                trySend(AppState.Success(FirebaseUtil.createPostData(it)))
            }

            awaitClose {}
        }



    }
}