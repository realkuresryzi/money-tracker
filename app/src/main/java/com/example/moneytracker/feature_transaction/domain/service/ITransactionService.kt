package com.example.moneytracker.feature_transaction.domain.service

import com.example.moneytracker.feature_transaction.domain.model.CategoryViewModel
import com.example.moneytracker.feature_transaction.domain.model.TransactionViewModel
import com.example.moneytracker.feature_transaction.domain.util.OrderType
import com.example.moneytracker.feature_transaction.domain.util.TransactionOrder
import kotlinx.coroutines.flow.Flow

interface ITransactionService {
    fun getTransactions(
        isExpenseFilter: Boolean? = null,
        categoryFilter: CategoryViewModel? = null,
        order: TransactionOrder = TransactionOrder.DATE,
        orderType: OrderType = OrderType.ASC
    ): Flow<List<TransactionViewModel>>

    suspend fun getTransaction(id: Int): TransactionViewModel
    suspend fun insertTransaction(transaction: TransactionViewModel)
    suspend fun deleteTransaction(transaction: TransactionViewModel)

    suspend fun getTotalIncomesByMonth(month: Int, year: Int): Double

    suspend fun getTotalExpensesByMonth(month: Int, year: Int): Double
}
