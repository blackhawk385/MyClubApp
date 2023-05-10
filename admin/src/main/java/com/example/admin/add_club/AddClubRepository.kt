package com.example.admin.add_club

import com.example.common.Club
import com.example.common.data.AppState
import com.example.common.persistance.FirebaseUtil
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

const val ADD_CLUB_COLLECTION = "clubs"

class AddClubRepository : AddClubContract {

    override fun addNewClub(club: Club): Flow<AppState<String>> {
        return flow {
            emit(AppState.Loading())
            if (!FirebaseUtil.addData(ADD_CLUB_COLLECTION, club)) {
                emit(AppState.Success("Data Succesfully added"))
            }else {
                emit(AppState.Error("error while adding"))
            }
        }
    }
}