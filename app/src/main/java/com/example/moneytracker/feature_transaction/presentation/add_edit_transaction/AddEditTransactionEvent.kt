package com.example.moneytracker.feature_transaction.presentation.add_edit_transaction

import android.net.Uri
import androidx.compose.ui.focus.FocusState
import com.example.moneytracker.feature_transaction.domain.model.CategoryModel

sealed class AddEditTransactionEvent {
    data class EnteredTitle(val value: String) : AddEditTransactionEvent()
    data class ChangeTitleFocus(val focusState: FocusState) : AddEditTransactionEvent()
    data class EnteredAmount(val value: String) : AddEditTransactionEvent()
    data class ChangeAmountFocus(val focusState: FocusState) : AddEditTransactionEvent()
    data class SelectCategory(val category: CategoryModel?) : AddEditTransactionEvent()
    data class UploadImage(val uri: Uri?) : AddEditTransactionEvent()
    data object Save : AddEditTransactionEvent()
}
