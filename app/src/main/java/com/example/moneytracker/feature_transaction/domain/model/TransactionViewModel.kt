package com.example.moneytracker.feature_transaction.domain.model

import android.net.Uri

data class TransactionViewModel(
    val id: Int,
    val title: String,
    val amount: Double,
    val category: CategoryViewModel,
    val date: String,
    val imageUri: Uri? = null,
)
