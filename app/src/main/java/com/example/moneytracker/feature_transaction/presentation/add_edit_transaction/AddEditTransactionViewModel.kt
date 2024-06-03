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
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditTransactionViewModel @Inject constructor(
    private val transactionService: ITransactionService,
    private val categoryService: ICategoryService,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _title = mutableStateOf("")
    val title: State<String> = _title

    private val _amount = mutableStateOf("")
    val amount: State<String> = _amount

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

    private var currentId: Int = 0
    private var getIncomeCategoriesJob: Job? = null
    private var getExpenseCategoriesJob: Job? = null

    init {
        savedStateHandle.get<Int>("id")?.let { id ->
            if (id != 0) {
                viewModelScope.launch {
                    transactionService.getTransaction(id).also { transaction ->
                        currentId = transaction.id
                        _title.value = transaction.title
                        _amount.value = transaction.amount.toString()
                        _category.value = transaction.category
                        _imageUri.value = transaction.imageUri
                    }
                }
            }
        }
        getIncomeCategories()
        getExpenseCategories()
    }

    fun onEvent(event: AddEditTransactionEvent) {
        when (event) {
            is AddEditTransactionEvent.EnteredTitle -> {
                _title.value = event.value
            }

            is AddEditTransactionEvent.EnteredAmount -> {
                _amount.value = event.value
            }

            is AddEditTransactionEvent.SelectCategory -> {
                _category.value =
                    if (_category.value == event.category) null
                    else event.category
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
                            title = title.value,
                            amount = amount.value.toDouble(),
                            category = category.value!!,
                            imageUri = imageUri.value,
                            date = "",
                            id = currentId
                        )
                    )
                    _eventFlow.emit(UiEvent.SaveTransaction)
                }
            }
        }
    }

    private fun getIncomeCategories() {
        getIncomeCategoriesJob?.cancel()
        getIncomeCategoriesJob = categoryService.getCategories(
            isExpenseFilter = false,
        )
            .onEach { categories ->
                _incomeCategories.value = categories
            }
            .launchIn(viewModelScope)
    }

    private fun getExpenseCategories() {
        getExpenseCategoriesJob?.cancel()
        getExpenseCategoriesJob = categoryService.getCategories(
            isExpenseFilter = true,
        )
            .onEach { categories ->
                _expenseCategories.value = categories
            }
            .launchIn(viewModelScope)
    }

    sealed class UiEvent {
        data object SaveTransaction : UiEvent()
    }
}