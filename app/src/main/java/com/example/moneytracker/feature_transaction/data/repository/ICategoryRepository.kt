package com.example.moneytracker.feature_transaction.data.repository

import com.example.moneytracker.feature_transaction.data.entity.Category
import kotlinx.coroutines.flow.Flow

interface ICategoryRepository {
    fun getCategories(isExpenseFilter: Boolean? = null): Flow<List<Category>>

    @Throws(EntityNotFoundException::class)
    suspend fun getCategoryById(id: Int): Category
    suspend fun insertCategory(category: Category)
    suspend fun deleteCategory(category: Category)
}