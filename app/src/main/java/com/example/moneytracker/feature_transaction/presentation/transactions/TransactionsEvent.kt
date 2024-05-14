package com.example.moneytracker.feature_transaction.presentation.transactions

import com.example.moneytracker.feature_transaction.domain.model.CategoryViewModel
import com.example.moneytracker.feature_transaction.domain.model.TransactionViewModel
import com.example.moneytracker.feature_transaction.domain.util.OrderType
import com.example.moneytracker.feature_transaction.domain.util.TransactionOrder

sealed class TransactionsEvent {
    data class Filter(
        val transactionOrder: TransactionOrder,
        val orderType: OrderType,
        val isExpenseFilter: Boolean? = null,
        val categoryFilter: CategoryViewModel? = null
    ) : TransactionsEvent()

    data class Delete(
        val transaction: TransactionViewModel
    ) : TransactionsEvent()

    data object Restore : TransactionsEvent()
    data object ToggleFilterBar : TransactionsEvent()
}