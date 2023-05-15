package com.example.common

import android.R.attr.maxLines
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import com.example.common.data.User


@Composable
fun DetailText(data: User) {
    Column() {
        Row {
            Text(
                text = "Member Name: ",
                fontSize = 20.sp,
                modifier = Modifier.alignByBaseline()
            )

            Text(
                text = data.fullName,
                fontSize = 12.sp,
                modifier = Modifier.alignByBaseline(),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        Row {
            Text(
                text = "Email/Phone: ",
                fontSize = 20.sp,
                modifier = Modifier.alignByBaseline()
            )

            Text(
                text = data.email,
                fontSize = 12.sp,
                modifier = Modifier.alignByBaseline(),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        Row {
            Text(
                text = "is also club owner: ",
                fontSize = 20.sp,
                modifier = Modifier.alignByBaseline()
            )

            Text(
                text = data.isAdmin.toString(),
                fontSize = 12.sp,
                modifier = Modifier.alignByBaseline()
            )
        }

        Row {
            Text(
                text = "City: ",
                fontSize = 20.sp,
                modifier = Modifier.alignByBaseline()
            )

            Text(
                text = data.city,
                fontSize = 12.sp,
                modifier = Modifier.alignByBaseline()
            )
        }

        Row {
            Text(
                text = "Gender: ",
                fontSize = 20.sp,
                modifier = Modifier.alignByBaseline()
            )

            Text(
                text = data.gender,
                fontSize = 12.sp,
                modifier = Modifier.alignByBaseline(),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        Row {
            Text(
                text = "DOB: ",
                fontSize = 20.sp,
                modifier = Modifier.alignByBaseline()
            )

            Text(
                text = data.dob,
                fontSize = 12.sp,
                modifier = Modifier.alignByBaseline(),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}