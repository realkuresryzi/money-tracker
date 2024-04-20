package com.example.moneytracker.domain.models

import android.net.Uri
import java.util.Date

data class Transaction(
    val id: Int,
    val description: String,
    val amount: Double,
    val date: Date,
    val category: TransactionCategory,
    val imageUri: Uri? = null
)
