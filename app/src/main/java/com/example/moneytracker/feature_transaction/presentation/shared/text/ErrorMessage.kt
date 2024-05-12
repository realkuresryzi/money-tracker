package com.example.moneytracker.feature_transaction.presentation.shared.text

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ErrorMessage(errorMessage: String) {
    Text(
        text = errorMessage,
        color = Color.Red,
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier
            .padding(top = 10.dp)
            .padding(horizontal = 15.dp)
    )
}