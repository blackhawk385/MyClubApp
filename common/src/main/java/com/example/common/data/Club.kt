package com.example.common

import com.example.common.data.User
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class Club(
    val uuid: String = UUID.randomUUID().toString(),
    val name: String = "",
    val location: String = "",
    val members: List<User> = listOf(),
    val requests: List<Request> = listOf(),
    val createdBy: User = User(),
    val list: List<Posts> = listOf()
)

@Serializable
data class Request(val requestedBy: User)

@Serializable
data class Posts(
    val uuid: String =  UUID.randomUUID().toString(),
    val title: String = "",
    val description: String = "",
    val comments: List<Comments> = listOf(),
    val author: User = User(),
    val associateClub : Club = Club(),
    val link: String = ""
)
@Serializable
data class Comments(val comment: String = "", val author: User = User(), val authoredOn: String = "")