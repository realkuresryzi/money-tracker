package com.example.moneytracker.feature_transaction.data.repository

import com.example.moneytracker.feature_transaction.data.entity.Category

interface ICategoryRepository {
    suspend fun getCategories(isExpenseFilter: Boolean? = null): List<Category>

    @Throws(EntityNotFoundException::class)
    suspend fun getCategoryById(id: Int): Category
    suspend fun insertCategory(category: Category)
    suspend fun deleteCategory(category: Category)
}