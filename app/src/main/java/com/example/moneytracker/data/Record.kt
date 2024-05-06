package com.example.moneytracker.data

import java.util.Date

data class Record(
    val id: String,
    val title: String,
    val category: Category,
    val createdAt: Date
)
