package com.example.moneytracker.feature_transaction.presentation.add_edit_category

sealed class AddEditCategoryEvent {
    data class EnteredName(val value: String) : AddEditCategoryEvent()
    data class EnteredIsExpense(val value: Boolean) : AddEditCategoryEvent()
    data object Save : AddEditCategoryEvent()
}
