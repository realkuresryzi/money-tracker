package com.example.moneytracker.feature_transaction.data

import android.graphics.Color
import com.example.moneytracker.R
import com.example.moneytracker.feature_transaction.data.entity.Category
import com.example.moneytracker.feature_transaction.data.entity.Transaction
import com.example.moneytracker.feature_transaction.data.storage.RoomDb
import java.util.*
import javax.inject.Inject

class DatabaseSeeder @Inject constructor(
    private val roomDb: RoomDb
) {
    suspend fun seedDatabase() {

        roomDb.transactionDao.deleteAllTransactions()
        roomDb.categoryDao.deleteAllCategories()

        val categories = listOf(
            Category(
                id = 1,
                name = "Food",
                color = Color.BLACK,
                isExpense = true,
                iconResourceId = 1
            ),
            Category(
                id = 2,
                name = "Transport",
                color = Color.BLUE,
                isExpense = true,
                iconResourceId = 1
            ),
        )
        roomDb.categoryDao.insertAll(categories)

        val transactions = listOf(
            Transaction(
                id = 1,
                title = "Lunch",
                amount = 15.0,
                categoryId = 1,
                createdAt = Date()),
            Transaction(
                id = 2,
                title = "Taxi ride",
                amount = 10.0,
                categoryId = 2,
                createdAt = Date()
            ),
        )
        roomDb.transactionDao.insertAll(transactions)

    }
}
