package com.example.moneytracker.ui.components.input

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.moneytracker.ui.components.text.ErrorMessage

@Composable
fun CustomInputField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String,
    modifier: Modifier = Modifier,
) {
    var text by remember { mutableStateOf(value) }
    var errorMessage by remember { mutableStateOf("") }

    errorMessage = when {
        text.isBlank() -> "Field is required"
        else -> ""
    }

    LaunchedEffect(value) {
        if (value.isBlank()) {
            text = value
        }
    }

    TextField(
        value = text,
        onValueChange = {
            text = it
            onValueChange(it)
        },
        label = { Text(label) },
        placeholder = { Text(placeholder) },
        singleLine = true,
        isError = errorMessage.isNotEmpty(),
        modifier = modifier
            .padding(horizontal = 15.dp)
            .fillMaxWidth()
    )
    if (errorMessage.isNotEmpty()) {
        ErrorMessage(errorMessage = errorMessage)
    }
}
