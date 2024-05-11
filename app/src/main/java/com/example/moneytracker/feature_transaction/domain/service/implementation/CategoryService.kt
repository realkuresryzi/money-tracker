package com.example.moneytracker.feature_transaction.domain.service.implementation

import com.example.moneytracker.feature_transaction.data.entity.Category
import com.example.moneytracker.feature_transaction.data.repository.ICategoryRepository
import com.example.moneytracker.feature_transaction.domain.mapper.EntityMapper
import com.example.moneytracker.feature_transaction.domain.model.CategoryModel
import com.example.moneytracker.feature_transaction.domain.service.ICategoryService

class CategoryService(
    private val repository: ICategoryRepository,
    private val categoryMapper: EntityMapper<Category, CategoryModel>
) : ICategoryService {
    override suspend fun getCategories(isExpenseFilter: Boolean?): List<CategoryModel> {
        return repository.getCategories(isExpenseFilter).map { category ->
            categoryMapper.entityToModel(category)
        }
    }

    override suspend fun getCategory(id: Int): CategoryModel {
        return categoryMapper.entityToModel(repository.getCategoryById(id))
    }

    override suspend fun insertCategory(category: CategoryModel) {
        repository.insertCategory(categoryMapper.modelToEntity(category))
    }

    override suspend fun deleteCategory(category: CategoryModel) {
        repository.deleteCategory(categoryMapper.modelToEntity(category))
    }
}