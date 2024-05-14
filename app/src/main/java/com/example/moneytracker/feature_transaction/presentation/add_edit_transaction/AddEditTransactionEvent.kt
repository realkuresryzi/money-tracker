package com.example.moneytracker.feature_transaction.presentation.add_edit_transaction

import android.net.Uri
import com.example.moneytracker.feature_transaction.domain.model.CategoryViewModel

sealed class AddEditTransactionEvent {
    data class EnteredTitle(val value: String) : AddEditTransactionEvent()
    data class ShowTitleErrorMessage(val message: String) : AddEditTransactionEvent()
    data class EnteredAmount(val value: String) : AddEditTransactionEvent()
    data class ShowAmountErrorMessage(val message: String) : AddEditTransactionEvent()
    data class SelectCategory(val category: CategoryViewModel?) : AddEditTransactionEvent()
    data class UploadImage(val uri: Uri?) : AddEditTransactionEvent()
    data object Save : AddEditTransactionEvent()
}
