package com.example.moneytracker.feature_transaction.data.repository

import com.example.moneytracker.feature_transaction.data.entity.Category
import com.example.moneytracker.feature_transaction.data.entity.Transaction
import kotlinx.coroutines.flow.Flow

interface ICategoryRepository {
    fun getCategories(isExpenseFilter: Boolean? = null): Flow<List<Category>>

    fun getTransactions(): Flow<List<Transaction>>

    @Throws(EntityNotFoundException::class)
    suspend fun getCategoryById(id: Int): Category
    suspend fun insertCategory(category: Category)
    suspend fun deleteCategory(category: Category)
}