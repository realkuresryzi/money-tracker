package com.example.moneytracker.ui.components.input

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.moneytracker.ui.components.text.ErrorMessage

@Composable
fun NumericInputField(
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
        text.toDoubleOrNull() == null -> "Invalid format"
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
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
        ),
        modifier = modifier
            .padding(horizontal = 15.dp)
            .fillMaxWidth()
    )
    if (errorMessage.isNotEmpty()) {
        ErrorMessage(errorMessage = errorMessage)
    }
}