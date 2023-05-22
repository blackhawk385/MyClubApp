package com.example.login.repository

import com.example.common.data.User
import com.example.common.data.AppState
import kotlinx.coroutines.flow.Flow

interface IRegisterRepository {
    fun registerUsingEmailAndPassword(email: String, password: String): Flow<AppState<User>>
    fun saveUserInfo(
        fullName: String,
        dob: String,
        gender: String,
        isAdmin: Boolean,
        city: String,
        uuid: String,
        email: String
    ): Boolean
}