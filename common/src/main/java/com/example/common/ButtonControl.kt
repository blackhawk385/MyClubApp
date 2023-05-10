package com.example.common

import android.content.Context
import android.widget.Toast
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.material.Button
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun ButtonControl(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    buttonText: String,
    enabled: Boolean = true
) {

//    val isButtonEnabled = rememberSaveable { mutableStateOf(true) }

    val aniamtedButtonColor = animateColorAsState(
        targetValue = if (enabled) Color.Blue else Color.White,
        animationSpec = tween(1000, 0, LinearEasing)
    )

    Button(
        onClick = { onClick() },
        modifier = modifier,
        enabled = enabled,
        border = BorderStroke(width = 2.dp, color = Color.Black),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = aniamtedButtonColor.value,
            contentColor = Color.White
        )
    ) {
        Text(text = buttonText)
    }
}

fun showMessage(context: Context, msg: String){
    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
}
