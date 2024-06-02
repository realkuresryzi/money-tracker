package com.example.moneytracker.feature_transaction.domain.service.implementation

import com.example.moneytracker.R
import com.example.moneytracker.feature_transaction.data.entity.Category
import com.example.moneytracker.feature_transaction.data.entity.Transaction
import com.example.moneytracker.feature_transaction.data.repository.ICategoryRepository
import com.example.moneytracker.feature_transaction.domain.mapper.Mapper
import com.example.moneytracker.feature_transaction.domain.model.CategoryViewModel
import com.example.moneytracker.feature_transaction.domain.model.TransactionViewModel
import com.example.moneytracker.feature_transaction.domain.service.ICategoryService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CategoryService(
    private val repository: ICategoryRepository,
    private val categoryEntityToModelMapper: Mapper<Category, CategoryViewModel>,
    private val categoryToModelToEntityMapper: Mapper<CategoryViewModel, Category>,
) : ICategoryService {

    override fun getCategories(isExpenseFilter: Boolean?): Flow<List<CategoryViewModel>> {
        val categoriesList = repository.getCategories(isExpenseFilter)
        val categoryModels = categoriesList.map { categories ->
            categories.map { category ->
                categoryEntityToModelMapper.map(category)
            }
        }
        return categoryModels
    }

    override suspend fun getCategory(id: Int): CategoryViewModel {
        val category = repository.getCategoryById(id)
        return categoryEntityToModelMapper.map(category)
    }

    override suspend fun insertCategory(category: CategoryViewModel) {
        val categoryEntity = categoryToModelToEntityMapper.map(category)
        repository.insertCategory(categoryEntity)
    }

    override suspend fun deleteCategory(category: CategoryViewModel): String {
        val transactions = repository.getTransactions().first()
        val hasTransactions = transactions.any { it.categoryId == category.id }

        if (!hasTransactions) {
            val categoryEntity = categoryToModelToEntityMapper.map(category)
            repository.deleteCategory(categoryEntity)
            return ""
        }

        // when in R.strings -> cannot determine type error
        return "Cannot delete because category contains some transactions"
    }

}