package com.example.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CustomCircularProgressBar(modifier: Modifier = Modifier.size(50.dp)) {
    Box(contentAlignment = Alignment.Center) {
        CircularProgressIndicator(
            modifier = modifier,
            color = Color.Green,
            strokeWidth = 10.dp
        )
    }
}