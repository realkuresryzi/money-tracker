package com.example.moneytracker.feature_transaction.domain.service

import com.example.moneytracker.feature_transaction.domain.model.CategoryViewModel
import kotlinx.coroutines.flow.Flow

interface ICategoryService {
    fun getCategories(isExpenseFilter: Boolean? = null): Flow<List<CategoryViewModel>>
    suspend fun getCategory(id: Int): CategoryViewModel
    suspend fun insertCategory(category: CategoryViewModel)
    suspend fun deleteCategory(category: CategoryViewModel): String
}
