package com.example.moneytracker.feature_transaction.domain.mapper

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.example.moneytracker.feature_transaction.data.entity.Category
import com.example.moneytracker.feature_transaction.domain.model.CategoryModel


class CategoryMapper : EntityMapper<Category, CategoryModel> {
    override suspend fun entityToModel(entity: Category): CategoryModel {
        return CategoryModel(
            id = entity.id,
            name = entity.name,
            color = Color(entity.color),
            isExpense = entity.isExpense,
            iconResourceId = entity.iconResourceId
        )
    }

    override fun modelToEntity(model: CategoryModel): Category {
        return Category(
            id = model.id,
            name = model.name,
            color = model.color.toArgb(),
            isExpense = model.isExpense,
            iconResourceId = model.iconResourceId
        )
    }
}