package com.example.moneytracker.feature_transaction.presentation.transactions

import com.example.moneytracker.feature_transaction.domain.model.CategoryModel
import com.example.moneytracker.feature_transaction.domain.model.TransactionModel
import com.example.moneytracker.feature_transaction.domain.util.OrderType
import com.example.moneytracker.feature_transaction.domain.util.TransactionOrder

sealed class TransactionsEvent {
    data class Filter(
        val transactionOrder: TransactionOrder,
        val orderType: OrderType,
        val isExpenseFiler: Boolean? = null,
        val categoryFilter: CategoryModel? = null
    ) : TransactionsEvent()

    data class Delete(
        val transaction: TransactionModel
    ) : TransactionsEvent()

    data object Restore : TransactionsEvent()
    data object ToggleFilterBar : TransactionsEvent()
}