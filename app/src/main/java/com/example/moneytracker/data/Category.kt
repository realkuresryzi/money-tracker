package com.example.moneytracker.data

import androidx.compose.ui.graphics.Color

data class Category(
    val id: String,
    val name: String,
    val color: Color,
    // not sure what type the icon we want to have
    val srcIcon: String,
    val expense: Boolean
)
