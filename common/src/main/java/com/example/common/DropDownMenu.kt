package com.example.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun DropDownMenu(
    expanded: Boolean,
    list: List<String>,
    onDismiss: (Boolean) -> Unit,
    onClick: (String) -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.TopCenter)
    ) {
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                onDismiss(expanded)
            },
            modifier = Modifier.fillMaxWidth(5f)
        ) {

            list.forEach {
                DropdownMenuItem(
                    onClick = {
                        onClick(it)
                    }
                ) {
                    Text(text = it)
                }
            }
        }
    }
}