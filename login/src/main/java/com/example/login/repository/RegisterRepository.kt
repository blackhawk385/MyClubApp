package com.example.login.repository

import android.util.Log
import com.example.common.data.User
import com.example.common.data.AppState
import com.example.common.persistance.FirebaseUtil
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class RegisterRepository : IRegisterRepository {
    override fun registerUsingEmailAndPassword(
        email: String,
        password: String
    ): Flow<AppState<User>> {
        return flow {
            emit(AppState.Loading())
            val user = Firebase.auth.createUserWithEmailAndPassword(email, email).await().user
            user?.let {
                emit(AppState.Success(createUser(it)))
            }
        }
    }

    private fun createUser(user: FirebaseUser) =
        User(uuid = user.uid, email = user.email!!)

    //firestore user info
    override fun saveUserInfo(
        fullName: String,
        dob: String,
        gender: String,
        isAdmin: Boolean,
        city: String,
        uuid: String,
        email: String
    ) = FirebaseUtil.addData(
            "user",
            User(
                uuid = uuid,
                fullName = fullName,
                city = city,
                dob = dob,
                isAdmin = isAdmin,
                gender = gender,
                email = email
            )
        )

}
