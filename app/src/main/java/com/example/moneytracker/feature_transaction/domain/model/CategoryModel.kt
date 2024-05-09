package com.example.moneytracker.feature_transaction.domain.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

data class CategoryModel(
    val id: Int,
    val name: String,
    val color: Color,
    val isExpense: Boolean,
    val iconResourceId: Int,
)
