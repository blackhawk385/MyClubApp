package com.example.admin.add_club

import com.example.common.Club
import com.example.common.data.AppState
import kotlinx.coroutines.flow.Flow

interface AddClubContract {
    fun addNewClub(club: Club) : Flow<AppState<String>>
}