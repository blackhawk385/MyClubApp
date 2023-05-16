package com.example.login.repository

import com.example.common.data.AppState
import com.example.common.data.User
import com.example.common.persistance.FirebaseUtil
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RegisterRepository : IRegisterRepository {
    override fun registerUsingEmailAndPassword(
        email: String,
        password: String
    ): Flow<AppState<User>> {
        return flow {
            emit(AppState.Loading())
            emit(AppState.Success(FirebaseUtil.registerFirebaseUser(email, password)!!))
        }
    }

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
