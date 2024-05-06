package com.example.moneytracker.data

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

data class Category(
    val id: String,
    val name: String,
    val color: Color,
    // for now imageVector, change it when db is implemented
    val icon: ImageVector,
    val isExpense: Boolean
)
