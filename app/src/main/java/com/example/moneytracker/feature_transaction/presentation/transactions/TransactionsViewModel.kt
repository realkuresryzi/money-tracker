package com.example.moneytracker.feature_transaction.presentation.transactions

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moneytracker.feature_transaction.domain.model.TransactionViewModel
import com.example.moneytracker.feature_transaction.domain.service.ICategoryService
import com.example.moneytracker.feature_transaction.domain.service.ITransactionService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionsViewModel @Inject constructor(
    private val transactionService: ITransactionService,
    private val categoryService: ICategoryService
) : ViewModel() {
    private val _state = mutableStateOf(TransactionsState())
    val state: State<TransactionsState> = _state

    private var recentlyDeleted: TransactionViewModel? = null

    init {
        viewModelScope.launch {
            _state.value = state.value.copy(
                categories = categoryService.getCategories(),
                transactions = transactionService.getTransactions(
                    state.value.isExpenseFilter,
                    state.value.categoryFilter,
                    state.value.transactionOrder,
                    state.value.orderType
                )
            )
        }
    }

    fun onEvent(event: TransactionsEvent) {
        when (event) {
            is TransactionsEvent.Filter -> {
                if (state.value.transactionOrder == event.transactionOrder
                    && state.value.orderType == event.orderType
                    && state.value.isExpenseFilter == event.isExpenseFilter
                ) {
                    return
                }
                val categoryFilter =
                    if (_state.value.categoryFilter == event.categoryFilter) null
                    else event.categoryFilter
                viewModelScope.launch {
                    _state.value = state.value.copy(
                        isExpenseFilter = event.isExpenseFilter,
                        categoryFilter = categoryFilter,
                        transactionOrder = event.transactionOrder,
                        orderType = event.orderType,
                        transactions = transactionService.getTransactions(
                            event.isExpenseFilter,
                            categoryFilter,
                            event.transactionOrder,
                            event.orderType
                        ),
                        categories = categoryService.getCategories(event.isExpenseFilter)
                    )
                }
            }

            is TransactionsEvent.Delete -> {
                viewModelScope.launch {
                    transactionService.deleteTransaction(event.transaction)
                    recentlyDeleted = event.transaction
                }
            }

            is TransactionsEvent.Restore -> {
                viewModelScope.launch {
                    transactionService.insertTransaction(recentlyDeleted ?: return@launch)
                    recentlyDeleted = null
                }
            }

            is TransactionsEvent.ToggleFilterBar -> {
                _state.value = state.value.copy(
                    isFilterBarVisible = !state.value.isFilterBarVisible
                )
            }
        }
    }
}