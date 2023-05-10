package com.example.common

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun InputTextField(
    modifier: Modifier = Modifier, placeHolder: String = "",
    label: String = "", value: String, enabled: Boolean = true, onValueChangeListner: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = {
            onValueChangeListner(it)
        },
        placeholder = { Text(text = "place holder text") },
        modifier = modifier,
        singleLine = true,
        maxLines = 1,
        label = {
            Text(label)
        },
    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        keyboardActions = KeyboardActions(onNext = {
//            LocalSoftwareKeyboardController.current?.hide()
//            LocalFocusManager.current.moveFocus((FocusDirection.Down)
        }),
        enabled = enabled
    )
}