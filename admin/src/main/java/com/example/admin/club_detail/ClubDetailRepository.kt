package com.example.admin.club_detail

import com.example.admin.add_club.ADD_CLUB_COLLECTION
import com.example.common.Club
import com.example.common.data.AppState
import com.example.common.persistance.FirebaseUtil
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ClubDetailRepository {

    fun getClubDetailsFromFirebase(uuid: String) : Flow<AppState<Club>> {
        return callbackFlow<AppState<Club>> {
               trySend(AppState.Loading())
            FirebaseUtil.getSingleDocument(ADD_CLUB_COLLECTION, uuid) {
                if(it.size > 0) {
                    trySend(AppState.Success(FirebaseUtil.createClubData(it)))
                }else {
                    trySend(AppState.Error(error("No data found")))
                }
            }

            awaitClose { }
        }
    }
}