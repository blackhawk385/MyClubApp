package com.example.common

import com.example.common.data.User
import java.util.UUID

data class Club(
    val uuid: String = UUID.randomUUID().toString(),
    val name: String = "",
    val location: String = "",
    val members: List<User> = listOf(),
    val requests: List<Request> = listOf(),
    val createdBy: User = User(),
    val list: List<Posts> = listOf()
)

data class Request(val requestedBy: User)


data class Posts(
    val title: String = "",
    val description: String = "",
    val comments: List<Comments> = listOf(),
    val author: User = User(),
    val associateClub : Club = Club(),
    val link: String = ""
)

data class Comments(val comment: String = "", val author: User = User(), val authoredOn: String = "")