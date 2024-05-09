package com.example.moneytracker.feature_transaction.domain.service

import com.example.moneytracker.feature_transaction.data.entity.Transaction
import com.example.moneytracker.feature_transaction.domain.model.TransactionModel
import kotlinx.coroutines.flow.Flow

interface ITransactionService {
    suspend fun getTransactions(
        isExpense: Boolean? = null,
        categoryId: Int? = null
    ): List<TransactionModel>

    suspend fun getTransaction(id: Int): TransactionModel
    suspend fun insertTransaction(transaction: TransactionModel)
    suspend fun deleteTransaction(transaction: TransactionModel)
}
