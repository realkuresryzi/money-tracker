package com.example.moneytracker.feature_transaction.domain.service.implementation

import com.example.moneytracker.feature_transaction.data.entity.Category
import com.example.moneytracker.feature_transaction.data.repository.ICategoryRepository
import com.example.moneytracker.feature_transaction.domain.mapper.Mapper
import com.example.moneytracker.feature_transaction.domain.model.CategoryViewModel
import com.example.moneytracker.feature_transaction.domain.service.ICategoryService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CategoryService(
    private val repository: ICategoryRepository,
    private val categoryEntityToModelMapper: Mapper<Category, CategoryViewModel>,
    private val categoryToModelToEntityMapper: Mapper<CategoryViewModel, Category>
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

    override suspend fun deleteCategory(category: CategoryViewModel) {
        // TODO check if there is some transacation in that category,
        // if so then throw some exception and show error message in UI
        // that you cannot delete cateogry because there is some transaction in it
        // so it will be possible to delete only empty categories
        // also make sure at least one income and one expense category exists
        // to avoid other problems in UI
        val categoryEntity = categoryToModelToEntityMapper.map(category)
        repository.deleteCategory(categoryEntity)
    }
}