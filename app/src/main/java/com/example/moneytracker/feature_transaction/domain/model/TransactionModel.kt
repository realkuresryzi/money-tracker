package com.example.moneytracker.feature_transaction.domain.model

import android.net.Uri
import java.util.Date

data class TransactionModel(
    val id: Int,
    val title: String,
    val amount: Double,
    val category: CategoryModel,
    val createdAt: Date,
    val imageUri: Uri? = null,
)
