package com.example.moneytracker.feature_transaction.domain.service

import com.example.moneytracker.feature_transaction.domain.model.CategoryModel
import com.example.moneytracker.feature_transaction.domain.model.TransactionModel
import com.example.moneytracker.feature_transaction.domain.util.OrderType
import com.example.moneytracker.feature_transaction.domain.util.TransactionOrder

interface ITransactionService {
    suspend fun getTransactions(
        isExpenseFilter: Boolean? = null,
        categoryFilter: CategoryModel? = null,
        order: TransactionOrder = TransactionOrder.DATE,
        orderType: OrderType = OrderType.ASC
    ): List<TransactionModel>

    suspend fun getTransaction(id: Int): TransactionModel
    suspend fun insertTransaction(transaction: TransactionModel)
    suspend fun deleteTransaction(transaction: TransactionModel)
}
