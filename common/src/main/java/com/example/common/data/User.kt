package com.example.common.data

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val uuid: String = "",
    val fullName: String = "",
    val city: String = "",
    val dob: String = "",
    val isAdmin: Boolean = false,
    val gender: String = "",
    val email: String = ""
)
