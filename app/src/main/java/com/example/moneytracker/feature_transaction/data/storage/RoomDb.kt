package com.example.moneytracker.feature_transaction.data.storage

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.moneytracker.feature_transaction.data.dao.CategoryDao
import com.example.moneytracker.feature_transaction.data.dao.TransactionDao
import com.example.moneytracker.feature_transaction.data.entity.Category
import com.example.moneytracker.feature_transaction.data.entity.Transaction
import com.example.moneytracker.feature_transaction.data.util.Converters

@Database(
    entities = [Category::class, Transaction::class],
    version = 2
)
@TypeConverters(Converters::class)
abstract class RoomDb : RoomDatabase() {
    abstract val categoryDao: CategoryDao
    abstract val transactionDao: TransactionDao

    companion object {
        const val DATABASE_NAME = "money_tracker_db"
    }
}