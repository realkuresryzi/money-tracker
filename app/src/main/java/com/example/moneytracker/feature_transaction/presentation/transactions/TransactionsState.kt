package com.example.moneytracker.feature_transaction.presentation.transactions

import com.example.moneytracker.feature_transaction.domain.model.CategoryModel
import com.example.moneytracker.feature_transaction.domain.model.TransactionModel
import com.example.moneytracker.feature_transaction.domain.util.OrderType
import com.example.moneytracker.feature_transaction.domain.util.TransactionOrder

data class TransactionsState(
    val transactions: List<TransactionModel> = emptyList(),
    val categories: List<CategoryModel> = emptyList(),
    val categoryFilter: CategoryModel? = null,
    val isExpenseFilter: Boolean? = null,
    val transactionOrder: TransactionOrder = TransactionOrder.DATE,
    val orderType: OrderType = OrderType.DESC,
    val isFilterBarVisible: Boolean = false
)