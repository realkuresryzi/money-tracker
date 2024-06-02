package com.example.moneytracker.feature_transaction.presentation.categories

import com.example.moneytracker.feature_transaction.domain.model.CategoryViewModel

sealed class CategoriesEvent {
    data class Delete(
        val category: CategoryViewModel
    ) : CategoriesEvent()

    data object Restore : CategoriesEvent()
}