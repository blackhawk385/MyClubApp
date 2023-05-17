package com.example.admin.admin_profile

import com.example.common.data.AppState
import com.example.common.data.User
import com.example.common.persistance.FirebaseUtil
import com.example.common.persistance.SharedPreference
import com.example.common.persistance.USER_COLLECTION
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class AdminProfileRepository {
    fun updateUserDetails(user: User) : Flow<AppState<String>>{
       return callbackFlow {
           trySend(AppState.Loading())
           FirebaseUtil.updateUserDetail(USER_COLLECTION, user){
               trySend(AppState.Success("User detail updated"))
           }
           awaitClose()
       }
    }
}