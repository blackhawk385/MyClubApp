package com.example.common.persistance

import com.example.common.Club
import com.example.common.Posts
import com.google.gson.Gson

fun  jsonToClubObject(json: String) =
    Gson().fromJson(json, Club::class.java)

fun  jsonToPostObject(json: String) =
    Gson().fromJson(json, Posts::class.java)

fun clubToJson(club: Club) = Gson().toJson(club)

fun postToJson(post: Posts) = Gson().toJson(post)

