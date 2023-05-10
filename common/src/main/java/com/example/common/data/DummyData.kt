package com.example.common.data

import com.example.common.Club
import com.example.common.Comments
import com.example.common.Posts
import com.example.common.Request

data class DummyData(
    val myClubs: List<Club> = arrayListOf(
        Club(
            name = "Club 1",
            members = arrayListOf(User(fullName = "Sarah")),
            requests = arrayListOf(Request(User("user that requested"))),
            createdBy = User("Mee"),
            list = arrayListOf(
                Posts(
                    title = "title 1",
                    comments = arrayListOf(
                        Comments(
                            comment = "comment 1",
                            author = User(fullName = "author 1"),
                            authoredOn = "12-12-90"
                        )
                    ),
                    author = User(fullName = "post author name")
                )
            )
        )
    )
)
