package com.example.moneytracker.feature_transaction.presentation.transactions

import com.example.moneytracker.feature_transaction.domain.model.CategoryViewModel
import com.example.moneytracker.feature_transaction.domain.model.TransactionViewModel
import com.example.moneytracker.feature_transaction.domain.util.OrderType
import com.example.moneytracker.feature_transaction.domain.util.TransactionOrder

data class TransactionsState(
    val transactions: List<TransactionViewModel> = emptyList(),
    val categories: List<CategoryViewModel> = emptyList(),
    val categoryFilter: CategoryViewModel? = null,
    val isExpenseFilter: Boolean? = null,
    val transactionOrder: TransactionOrder = TransactionOrder.DATE,
    val orderType: OrderType = OrderType.DESC,
    val isFilterBarVisible: Boolean = false,
    val balance: Double = 0.0,
    val isFABVisible: Boolean = true
)