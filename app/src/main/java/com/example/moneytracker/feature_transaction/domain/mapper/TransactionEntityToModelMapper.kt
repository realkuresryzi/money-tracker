package com.example.moneytracker.feature_transaction.domain.mapper

import com.example.moneytracker.feature_transaction.data.entity.Category
import com.example.moneytracker.feature_transaction.data.entity.TransactionWithCategory
import com.example.moneytracker.feature_transaction.domain.model.CategoryViewModel
import com.example.moneytracker.feature_transaction.domain.model.TransactionViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class TransactionEntityToModelMapper(
    private val categoryMapper: Mapper<Category, CategoryViewModel>
) : Mapper<TransactionWithCategory, TransactionViewModel> {
    private val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")

    override fun map(source: TransactionWithCategory): TransactionViewModel {
        return TransactionViewModel(
            id = source.transaction.id,
            title = source.transaction.title,
            amount = source.transaction.amount,
            category = categoryMapper.map(source.category),
            date = formatDate(source.transaction.createdAt),
            imageUri = source.transaction.imageUri,
        )
    }

    private fun formatDate(dateTime: LocalDateTime): String {
        return dateFormatter.format(dateTime)
    }
}