package com.example.moneytracker.feature_transaction.presentation.shared.input

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.moneytracker.feature_transaction.presentation.shared.text.ErrorMessage

@Composable
fun CustomInputField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String,
    modifier: Modifier = Modifier,
) {
    var errorMessage by remember { mutableStateOf("") }

    errorMessage = when {
        value.isBlank() -> "Field is required"
        else -> ""
    }

    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        placeholder = { Text(placeholder) },
        singleLine = true,
        isError = errorMessage.isNotBlank(),
        modifier = modifier
            .fillMaxWidth()
    )
    if (errorMessage.isNotEmpty()) {
        ErrorMessage(errorMessage = errorMessage)
    }
}
