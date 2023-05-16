package com.example.login.repository

import com.example.common.data.AppState
import com.example.common.data.User
import com.example.common.persistance.FirebaseUtil.createUser
import com.example.common.persistance.FirebaseUtil.loginFirebaseUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LoginRepository : ILoginRepository {
    override fun authWithEmailAndPassword(email: String, password: String): Flow<AppState<User>> {
         return flow {
            emit(AppState.Loading())
            emit(AppState.Success(loginFirebaseUser(email, email)!!))
        }
    }
}