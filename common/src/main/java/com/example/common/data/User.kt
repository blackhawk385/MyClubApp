package com.example.common.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
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
