package com.example.moneytracker.feature_transaction.domain.mapper

import android.net.Uri
import com.example.moneytracker.feature_transaction.data.entity.Category
import com.example.moneytracker.feature_transaction.data.entity.Transaction
import com.example.moneytracker.feature_transaction.data.repository.ICategoryRepository
import com.example.moneytracker.feature_transaction.domain.model.CategoryModel
import com.example.moneytracker.feature_transaction.domain.model.TransactionModel

class TransactionMapper(
    private val categoryRepository: ICategoryRepository,
    private val categoryMapper: EntityMapper<Category, CategoryModel>
) : EntityMapper<Transaction, TransactionModel> {
    override suspend fun entityToModel(entity: Transaction): TransactionModel {
        val categoryEntity = categoryRepository.getCategoryById(entity.categoryId)
        return TransactionModel(
            id = entity.id,
            title = entity.title,
            amount = entity.amount,
            category = categoryMapper.entityToModel(categoryEntity),
            createdAt = entity.createdAt,
            imageUri = entity.imageUri?.let { Uri.parse(it) },
        )
    }

    override fun modelToEntity(model: TransactionModel): Transaction {
        return Transaction(
            id = model.id,
            title = model.title,
            amount = model.amount,
            categoryId = model.category.id,
            createdAt = model.createdAt,
            imageUri = model.imageUri.toString()
        )
    }
}