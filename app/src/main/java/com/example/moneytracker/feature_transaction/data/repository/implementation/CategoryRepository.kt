package com.example.moneytracker.feature_transaction.data.repository.implementation

import com.example.moneytracker.feature_transaction.data.dao.CategoryDao
import com.example.moneytracker.feature_transaction.data.entity.Category
import com.example.moneytracker.feature_transaction.data.repository.EntityNotFoundException
import com.example.moneytracker.feature_transaction.data.repository.ICategoryRepository

class CategoryRepository(
    private val dao: CategoryDao
) : ICategoryRepository {
    override suspend fun getCategories(isExpenseFilter: Boolean?): List<Category> {
        return dao.getCategories()
    }

    @Throws(EntityNotFoundException::class)
    override suspend fun getCategoryById(id: Int): Category {
        return dao.getCategoryById(id)
            ?: throw EntityNotFoundException("Could not find category with id $id")
    }

    override suspend fun insertCategory(category: Category) {
        dao.insertCategory(category)
    }

    override suspend fun deleteCategory(category: Category) {
        dao.deleteCategory(category)
    }
}