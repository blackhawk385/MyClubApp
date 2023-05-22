package com.example.common

import android.os.Parcelable
import com.example.common.data.User
import kotlinx.android.parcel.Parcelize
import kotlinx.serialization.Serializable
import java.util.UUID

data class Club(
    val uuid: String = UUID.randomUUID().toString(),
    val name: String = "",
    val location: String = "",
    val numberOfRooms: String = "",
    val members: ArrayList<User> = arrayListOf(),
    val createdBy: User = User(),
)

data class Request(val requestedBy: User, val club: Club)

data class Posts(
    val uuid: String = UUID.randomUUID().toString(),
    val title: String = "",
    val description: String = "",
    val author: User = User(),
    val associateClub: Club = Club(),
    val link: String = ""
)

data class Comments(
    val comment: String = "",
    val author: User = User(),
    val authoredOn: String = "",
    val associatedPostUUID: String = ""
)