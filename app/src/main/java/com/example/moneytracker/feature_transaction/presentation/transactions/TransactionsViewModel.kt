package com.example.moneytracker.feature_transaction.presentation.transactions

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moneytracker.feature_transaction.domain.model.CategoryViewModel
import com.example.moneytracker.feature_transaction.domain.model.TransactionViewModel
import com.example.moneytracker.feature_transaction.domain.service.ICategoryService
import com.example.moneytracker.feature_transaction.domain.service.ITransactionService
import com.example.moneytracker.feature_transaction.domain.util.OrderType
import com.example.moneytracker.feature_transaction.domain.util.TransactionOrder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
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
    private var getTransactionsJob: Job? = null
    private var getCategoriesJob: Job? = null

    init {
        getTransactions(
            isExpenseFilter = state.value.isExpenseFilter,
            categoryFilter = state.value.categoryFilter,
            transactionOrder = state.value.transactionOrder,
            orderType = state.value.orderType
        )
        getCategories(
            isExpenseFilter = state.value.isExpenseFilter
        )
    }

    fun onEvent(event: TransactionsEvent) {
        when (event) {
            is TransactionsEvent.Filter -> {
                val categoryFilter =
                    if (_state.value.categoryFilter == event.categoryFilter) null
                    else event.categoryFilter
                getTransactions(
                    isExpenseFilter = event.isExpenseFilter,
                    categoryFilter = categoryFilter,
                    transactionOrder = event.transactionOrder,
                    orderType = event.orderType
                )
                getCategories(
                    isExpenseFilter = event.isExpenseFilter
                )
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

    private fun getTransactions(
        isExpenseFilter: Boolean?,
        categoryFilter: CategoryViewModel?,
        orderType: OrderType,
        transactionOrder: TransactionOrder
    ) {
        getTransactionsJob?.cancel()
        getTransactionsJob = transactionService.getTransactions(
            isExpenseFilter = isExpenseFilter,
            categoryFilter = categoryFilter,
            orderType = orderType,
            order = transactionOrder
        )
            .onEach { transactions ->
                _state.value = state.value.copy(
                    transactions = transactions,
                    isExpenseFilter = isExpenseFilter,
                    categoryFilter = categoryFilter,
                    orderType = orderType,
                    transactionOrder = transactionOrder,
                    balance = transactions
                        .map { it.amount }
                        .reduceOrNull { acc, amount -> acc + amount } ?: 0.0
                )
            }
            .launchIn(viewModelScope)
    }

    private fun getCategories(
        isExpenseFilter: Boolean?,
    ) {
        getCategoriesJob?.cancel()
        getCategoriesJob = categoryService.getCategories(
            isExpenseFilter = isExpenseFilter,
        )
            .onEach { categories ->
                _state.value = state.value.copy(
                    categories = categories,
                )
            }
            .launchIn(viewModelScope)
    }
}