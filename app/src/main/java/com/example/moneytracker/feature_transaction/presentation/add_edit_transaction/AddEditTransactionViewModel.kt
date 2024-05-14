package com.example.moneytracker.feature_transaction.presentation.add_edit_transaction

import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moneytracker.feature_transaction.domain.model.CategoryViewModel
import com.example.moneytracker.feature_transaction.domain.model.TransactionViewModel
import com.example.moneytracker.feature_transaction.domain.service.ICategoryService
import com.example.moneytracker.feature_transaction.domain.service.ITransactionService
import com.example.moneytracker.feature_transaction.domain.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditTransactionViewModel @Inject constructor(
    private val transactionService: ITransactionService,
    private val categoryService: ICategoryService,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _title = mutableStateOf(
        InputFieldState(
            hint = Constants.TEXT_INPUT_PLACEHOLDER
        )
    )
    val title: State<InputFieldState> = _title

    private val _amount = mutableStateOf(
        InputFieldState(
            hint = Constants.NUMERIC_INPUT_PLACEHOLDER
        )
    )
    val amount: State<InputFieldState> = _amount

    private val _category = mutableStateOf<CategoryViewModel?>(null)
    val category: State<CategoryViewModel?> = _category

    private val _imageUri = mutableStateOf<Uri?>(null)
    val imageUri: State<Uri?> = _imageUri

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _incomeCategories = mutableStateOf<List<CategoryViewModel>>(emptyList())
    val incomeCategories: State<List<CategoryViewModel>> = _incomeCategories

    private val _expenseCategories = mutableStateOf<List<CategoryViewModel>>(emptyList())
    val expenseCategories: State<List<CategoryViewModel>> = _expenseCategories

    private var currentId: Int? = null

    init {
        savedStateHandle.get<Int>("id")?.let { id ->
            if (id != -1) {
                viewModelScope.launch {
                    transactionService.getTransaction(id).also { transaction ->
                        currentId = transaction.id
                        _title.value = title.value.copy(
                            text = transaction.title,
                            isHintVisible = false
                        )
                        _amount.value = amount.value.copy(
                            text = transaction.amount.toString(),
                            isHintVisible = false
                        )
                        _category.value = transaction.category
                        _imageUri.value = transaction.imageUri
                    }
                }
            }
        }
        viewModelScope.launch {
            _expenseCategories.value = categoryService.getCategories(isExpenseFilter = true)
        }
        viewModelScope.launch {
            _incomeCategories.value = categoryService.getCategories(isExpenseFilter = false)
        }
    }

    fun onEvent(event: AddEditTransactionEvent) {
        when (event) {
            is AddEditTransactionEvent.EnteredTitle -> {
                _title.value = title.value.copy(
                    text = event.value
                )
            }

            is AddEditTransactionEvent.ShowTitleErrorMessage -> {
//                _title.value = title.value.copy(
//                    isHintVisible = !event.message.isFocused && title.value.text.isBlank()
//                )
            }
            // todo change it to number
            is AddEditTransactionEvent.EnteredAmount -> {
                _amount.value = amount.value.copy(
                    text = event.value
                )
            }

            is AddEditTransactionEvent.ShowAmountErrorMessage -> {
//                _amount.value = amount.value.copy(
//                    isHintVisible = !event.message.isFocused && amount.value.text.isBlank()
//                )
            }

            is AddEditTransactionEvent.SelectCategory -> {
                if (_category.value == event.category) {
                    _category.value = null
                } else {
                    _category.value = event.category
                }
            }

            is AddEditTransactionEvent.UploadImage -> {
                _imageUri.value = event.uri
            }

            is AddEditTransactionEvent.Save -> {
                viewModelScope.launch {
                    if (category.value == null) {
                        return@launch
                    }
                    transactionService.insertTransaction(
                        TransactionViewModel(
                            title = title.value.text,
                            amount = amount.value.text.toDouble(),
                            category = category.value!!,
                            imageUri = imageUri.value,
                            // date and id generated by db
                            date = "",
                            id = -1
                        )
                    )
                    _eventFlow.emit(UiEvent.SaveTransaction)
                }
            }
        }
    }

    sealed class UiEvent {
        data object SaveTransaction : UiEvent()
    }
}