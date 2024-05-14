package com.example.moneytracker.feature_transaction.data.repository

import com.example.moneytracker.feature_transaction.data.entity.Transaction
import com.example.moneytracker.feature_transaction.data.entity.TransactionWithCategory
import com.example.moneytracker.feature_transaction.domain.util.Constants

interface ITransactionRepository {
    suspend fun getTransactions(
        isExpenseFilter: Boolean? = null,
        categoryFilter: Int? = null,
        order: String = Constants.DATE,
        orderAsc: Boolean = true
    ): List<TransactionWithCategory>

    @Throws(EntityNotFoundException::class)
    suspend fun getTransactionById(id: Int): TransactionWithCategory
    suspend fun insertTransaction(transaction: Transaction)
    suspend fun deleteTransaction(transaction: Transaction)

    suspend fun getTotalIncomesByMonth(
        month: Int,
        year: Int
    ): Double

    suspend fun getTotalExpensesByMonth(
        month: Int,
        year: Int
    ): Double
}