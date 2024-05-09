package com.example.moneytracker.feature_transaction.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.moneytracker.feature_transaction.domain.model.CategoryModel
import java.util.Date

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = CategoryModel::class,
            parentColumns = ["id"],
            childColumns = ["categoryId"],
            onDelete = ForeignKey.NO_ACTION
        )
    ]
)
data class Transaction(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val amount: Double,
    val categoryId: Int,
    val createdAt: Date = Date(),
    val imageUri: String? = null
)
