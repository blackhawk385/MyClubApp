package com.example.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun AllClubs(clubList: List<Club>, onListItemClick: (Int) -> Unit) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(clubList.size) {
            Column {
                Text(text = clubList[it].name, modifier = Modifier
                    .padding(10.dp)
                    .clickable {
                       onListItemClick(it)
                    })
                Divider(thickness = 2.dp)
            }
        }
    }
}

@Composable
fun ShowClubList(
    list: List<Club>,
    onItemClick: (Int) -> Unit,
    msg: String
) {
    if (list.isEmpty()) {
        Text(text = msg)
    } else {
        AllClubs(list){
            onItemClick(it)
        }
    }
}

@Composable
fun PostList(postList : List<Posts>, onItemClick: (Int) -> Unit) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(postList.size) {
            Column {
                Text(
                    text = postList[it].title,
                    modifier = Modifier
                        .padding(10.dp)
                        .clickable {
                            onItemClick(it)
                        },
                    fontSize = 14.sp
                )
                Divider(thickness = 2.dp)
            }

        }
    }
}

