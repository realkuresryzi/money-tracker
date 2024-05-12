package com.example.moneytracker.feature_transaction.data.repository

import com.example.moneytracker.feature_transaction.data.entity.Transaction
import com.example.moneytracker.feature_transaction.domain.util.Constants

interface ITransactionRepository {
    suspend fun getTransactions(
        isExpenseFilter: Boolean? = null,
        categoryFilter: Int? = null,
        order: String = Constants.DATE,
        orderAsc: Boolean = true
    ): List<Transaction>

    @Throws(EntityNotFoundException::class)
    suspend fun getTransactionById(id: Int): Transaction
    suspend fun insertTransaction(transaction: Transaction)
    suspend fun deleteTransaction(transaction: Transaction)
}