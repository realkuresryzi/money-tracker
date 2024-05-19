package com.example.moneytracker.feature_transaction.data.entity

import androidx.room.Embedded

data class TransactionWithCategory(
    @Embedded val transaction: Transaction,
    @Embedded(prefix = "category_") val category: Category
)