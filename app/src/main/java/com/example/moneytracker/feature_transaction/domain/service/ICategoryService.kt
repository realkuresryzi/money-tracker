package com.example.moneytracker.feature_transaction.domain.service

import com.example.moneytracker.feature_transaction.domain.model.CategoryViewModel

interface ICategoryService {
    suspend fun getCategories(isExpenseFilter: Boolean? = null): List<CategoryViewModel>
    suspend fun getCategory(id: Int): CategoryViewModel
    suspend fun insertCategory(category: CategoryViewModel)
    suspend fun deleteCategory(category: CategoryViewModel)
}
