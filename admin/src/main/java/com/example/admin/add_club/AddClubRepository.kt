package com.example.admin.add_club

import com.example.common.Club
import com.example.common.data.AppState
import com.example.common.persistance.FirebaseUtil
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow

const val CLUB_COLLECTION = "clubs"

class AddClubRepository : AddClubContract {

    override fun addNewClub(club: Club): Flow<AppState<String>> {
        return flow {
            emit(AppState.Loading())
            if (!FirebaseUtil.addData(CLUB_COLLECTION, club)) {
                emit(AppState.Success("Data Succesfully added"))
            }else {
                emit(AppState.Error("error while adding"))
            }
        }
    }

    //update
    override fun updateClub(club: Club): Flow<AppState<String>> {
        return callbackFlow {
            trySend(AppState.Loading())
            FirebaseUtil.updateClubDetail(CLUB_COLLECTION, club){
                trySend(AppState.Success("Update Successfull"))
            }
            awaitClose { }
        }
    }
}