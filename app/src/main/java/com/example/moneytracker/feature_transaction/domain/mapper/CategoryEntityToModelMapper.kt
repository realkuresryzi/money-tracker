package com.example.moneytracker.feature_transaction.domain.mapper

import androidx.compose.ui.graphics.Color
import com.example.moneytracker.feature_transaction.data.entity.Category
import com.example.moneytracker.feature_transaction.domain.model.CategoryViewModel


class CategoryEntityToModelMapper : Mapper<Category, CategoryViewModel> {
    override fun map(source: Category): CategoryViewModel {
        return CategoryViewModel(
            id = source.id,
            name = source.name,
            color = Color(source.color),
            isExpense = source.isExpense,
            iconResourceId = source.iconResourceId
        )
    }
}