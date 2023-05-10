package com.example.login.repository

import android.util.Log
import com.example.common.data.User
import com.example.common.data.AppState
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class LoginRepository : ILoginRepository {
    override fun authWithEmailAndPassword(email: String, password: String): Flow<AppState<User>> {
         return flow {
            emit(AppState.Loading())
            val user = Firebase.auth.signInWithEmailAndPassword(email, email).await().user
             user?.let {
                 emit(AppState.Success(createUser(it)))
             }
        }
    }

    fun createUser(user: FirebaseUser): User {
        return User(uuid = user.uid, email = user.email!!)
    }
}