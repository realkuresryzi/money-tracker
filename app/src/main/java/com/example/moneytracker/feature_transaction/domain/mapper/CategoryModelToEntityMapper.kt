package com.example.moneytracker.feature_transaction.domain.mapper

import androidx.compose.ui.graphics.toArgb
import com.example.moneytracker.feature_transaction.data.entity.Category
import com.example.moneytracker.feature_transaction.domain.model.CategoryViewModel

class CategoryModelToEntityMapper : Mapper<CategoryViewModel, Category> {
    override fun map(source: CategoryViewModel): Category {
        return Category(
            id = source.id,
            name = source.name,
            color = source.color.toArgb(),
            isExpense = source.isExpense,
            iconResourceId = source.iconResourceId
        )
    }
}