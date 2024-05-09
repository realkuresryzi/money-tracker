package com.example.moneytracker.feature_transaction.data.storage

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.moneytracker.feature_transaction.data.dao.CategoryDao
import com.example.moneytracker.feature_transaction.data.dao.TransactionDao
import com.example.moneytracker.feature_transaction.data.entity.Category
import com.example.moneytracker.feature_transaction.data.entity.Transaction

@Database(
    entities = [Category::class, Transaction::class],
    version = 1
)
abstract class RoomDb : RoomDatabase() {
    abstract val categoryDao: CategoryDao
    abstract val transactionDao: TransactionDao

    companion object {
        const val DATABASE_NAME = "money_tracker_db"
    }
}