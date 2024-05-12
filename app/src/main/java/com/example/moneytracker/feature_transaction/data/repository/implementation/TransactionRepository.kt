package com.example.moneytracker.feature_transaction.data.repository.implementation

import com.example.moneytracker.feature_transaction.data.dao.TransactionDao
import com.example.moneytracker.feature_transaction.data.entity.Transaction
import com.example.moneytracker.feature_transaction.data.repository.EntityNotFoundException
import com.example.moneytracker.feature_transaction.data.repository.ITransactionRepository

class TransactionRepository(
    private val dao: TransactionDao
) : ITransactionRepository {
    override suspend fun getTransactions(
        isExpenseFilter: Boolean?,
        categoryFilter: Int?,
        order: String,
        orderAsc: Boolean
    ): List<Transaction> {
        return dao.getTransactions(isExpenseFilter, categoryFilter, order, orderAsc)
    }

    @Throws(EntityNotFoundException::class)
    override suspend fun getTransactionById(id: Int): Transaction {
        return dao.getTransactionById(id)
            ?: throw EntityNotFoundException("Could not find transaction with id $id")
    }

    override suspend fun insertTransaction(transaction: Transaction) {
        dao.insertTransaction(transaction)
    }

    override suspend fun deleteTransaction(transaction: Transaction) {
        dao.deleteTransaction(transaction)
    }
}