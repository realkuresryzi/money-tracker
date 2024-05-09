package com.example.moneytracker.feature_transaction.data.repository

import com.example.moneytracker.feature_transaction.data.entity.Category
import kotlinx.coroutines.flow.Flow

interface ICategoryRepository {
    suspend fun getCategories(): List<Category>
    suspend fun getCategoryById(id: Int): Category
    suspend fun insertCategory(category: Category)
    suspend fun deleteCategory(category: Category)
}