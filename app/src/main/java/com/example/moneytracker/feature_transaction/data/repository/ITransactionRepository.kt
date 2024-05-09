package com.example.moneytracker.feature_transaction.data.repository

import com.example.moneytracker.feature_transaction.data.entity.Transaction
import kotlinx.coroutines.flow.Flow

interface ITransactionRepository {
    suspend fun getTransactions(
        isExpense: Boolean? = null,
        categoryId: Int? = null
    ): List<Transaction>

    suspend fun getTransactionById(id: Int): Transaction
    suspend fun insertTransaction(transaction: Transaction)
    suspend fun deleteTransaction(transaction: Transaction)
}