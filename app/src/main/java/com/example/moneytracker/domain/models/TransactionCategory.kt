package com.example.moneytracker.domain.models

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

data class TransactionCategory(
    val id: Int,
    val name: String,
    val icon: ImageVector,
    val color: Color
)
