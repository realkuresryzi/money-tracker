package com.example.moneytracker.feature_transaction.data.entity

import android.net.Uri
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = Category::class,
            parentColumns = ["id"],
            childColumns = ["categoryId"],
            onDelete = ForeignKey.NO_ACTION
        )
    ],
    indices = [Index("categoryId")]
)
data class Transaction(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 1,
    val title: String,
    val amount: Double,
    val categoryId: Int,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val imageUri: Uri? = null
)
