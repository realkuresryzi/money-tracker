package com.example.moneytracker.data

import android.net.Uri
import java.util.Date

data class Transaction(
    val id: String,
    val title: String,
    val amount: Double,
    val category: Category,
    val createdAt: Date,
    // not sure if theres going to be image
    val imageUri: Uri? = null
)
