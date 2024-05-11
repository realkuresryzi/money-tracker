package com.example.moneytracker.feature_transaction.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Category(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val color: Int,
    val isExpense: Boolean,
    val iconResourceId: Int
)