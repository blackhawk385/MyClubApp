package com.example.login.repository

import com.example.common.data.User
import com.example.common.data.AppState
import kotlinx.coroutines.flow.Flow

interface ILoginRepository {
    fun authWithEmailAndPassword(email: String, password: String) : Flow<AppState<User>>
}