package com.example.moneytracker.feature_transaction.domain.service

import com.example.moneytracker.feature_transaction.domain.model.CategoryModel
import kotlinx.coroutines.flow.Flow

interface ICategoryService {
    suspend fun getCategories(): List<CategoryModel>
    suspend fun getCategory(id: Int): CategoryModel
    suspend fun insertCategory(category: CategoryModel)
    suspend fun deleteCategory(category: CategoryModel)
}
