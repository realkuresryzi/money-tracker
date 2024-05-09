package com.example.moneytracker.feature_transaction.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.moneytracker.ui.theme.Pink40
import com.example.moneytracker.ui.theme.Pink80
import com.example.moneytracker.ui.theme.Purple40
import com.example.moneytracker.ui.theme.Purple80
import com.example.moneytracker.ui.theme.PurpleGrey40
import com.example.moneytracker.ui.theme.PurpleGrey80

@Entity
data class Category(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val color: Int,
    val isExpense: Boolean,
    val iconResourceId: Int
) {
    companion object {
        val colors = listOf(Purple80, PurpleGrey80, Pink80, Purple40, PurpleGrey40, Pink40)
    }
}