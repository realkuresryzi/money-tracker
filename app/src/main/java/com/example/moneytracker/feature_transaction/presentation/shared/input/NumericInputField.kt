package com.example.moneytracker.feature_transaction.presentation.shared.input

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.moneytracker.R
import com.example.moneytracker.feature_transaction.presentation.shared.text.ErrorMessage

@Composable
fun NumericInputField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String,
    modifier: Modifier = Modifier,
) {
    var errorMessage by remember { mutableStateOf("") }


    errorMessage = when {
        value.isBlank() -> stringResource(R.string.required_field)
        value.toDoubleOrNull() == null -> stringResource(R.string.invalid_format)
        else -> ""
    }

    TextField(
        value = value,
        onValueChange = { onValueChange(it) },
        label = { Text(label) },
        placeholder = { Text(placeholder) },
        singleLine = true,
        isError = errorMessage.isNotEmpty(),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
        ),
        modifier = modifier
            .fillMaxWidth()
    )
    if (errorMessage.isNotEmpty()) {
        ErrorMessage(errorMessage = errorMessage, modifier = modifier.padding(top = 10.dp))
    }
}
