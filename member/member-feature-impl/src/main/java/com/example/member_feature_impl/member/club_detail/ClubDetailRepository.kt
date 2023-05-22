package com.example.member_feature_impl.member.club_detail

import com.example.common.Club
import com.example.common.Posts
import com.example.common.Request
import com.example.common.data.AppState
import com.example.common.persistance.CLUB_COLLECTION
import com.example.common.persistance.FirebaseUtil
import com.example.common.persistance.POST_COLLECTION
import com.example.common.persistance.REQUEST_COLLECTION
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow

class ClubDetailRepository {

    fun getClubDetailsFromFirebase(uuid: String) : Flow<AppState<Club>> {
        return callbackFlow<AppState<Club>> {
            trySend(AppState.Loading())
            FirebaseUtil.getSingleDocument(CLUB_COLLECTION, uuid) {
                if(it.size > 0) {
                    trySend(AppState.Success(FirebaseUtil.createClubData(it)))
                }else {
                    trySend(AppState.Error(error("No data found")))
                }
            }

            awaitClose { }
        }
    }

    fun getPostsFromFirebase(uuid: String) : Flow<AppState<List<Posts>>> {
        return callbackFlow<AppState<List<Posts>>> {
            trySend(AppState.Loading())
            FirebaseUtil.getConditionalListOfDocumentSnapShot(POST_COLLECTION, "associateClub.uuid", uuid) {
                if(it.size > 0) {
                    trySend(AppState.Success(FirebaseUtil.createPostData(it)))
                }else {
//                    trySend(AppState.Error(error("No data found")))
                }
            }

            awaitClose { }
        }
    }

    fun addRequest(request: Request) : Flow<AppState<Any>>{
        return flow {
            emit(AppState.Success(FirebaseUtil.addData(REQUEST_COLLECTION, request)))
        }
    }

    fun getClubJoinRequestFromFirebase(collection: String, uuid: String): Flow<AppState<List<Request>>>{
        return callbackFlow {
            FirebaseUtil.getConditionalListOfDocumentSnapShot(collection, "club.uuid", uuid){
                trySend(AppState.Success(FirebaseUtil.createListOfRequestData(it)))
            }
        }
    }

}