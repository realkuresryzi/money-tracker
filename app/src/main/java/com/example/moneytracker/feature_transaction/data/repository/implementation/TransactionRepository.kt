package com.example.moneytracker.feature_transaction.data.repository.implementation

import com.example.moneytracker.feature_transaction.data.dao.TransactionDao
import com.example.moneytracker.feature_transaction.data.entity.Transaction
import com.example.moneytracker.feature_transaction.data.entity.TransactionWithCategory
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
    ): List<TransactionWithCategory> {
        return dao.getTransactions(isExpenseFilter, categoryFilter, order, orderAsc)
    }

    @Throws(EntityNotFoundException::class)
    override suspend fun getTransactionById(id: Int): TransactionWithCategory {
        return dao.getTransactionById(id)
            ?: throw EntityNotFoundException("Could not find transaction with id $id")
    }

    override suspend fun insertTransaction(transaction: Transaction) {
        dao.insertTransaction(transaction)
    }

    override suspend fun deleteTransaction(transaction: Transaction) {
        dao.deleteTransaction(transaction)
    }

    override suspend fun getTotalIncomesByMonth(
        month: Int,
        year: Int
    ): Double {
        return dao.getTotalIncomeByMonth(month, year)
    }

    override suspend fun getTotalExpensesByMonth(
        month: Int,
        year: Int
    ): Double {
        return dao.getTotalExpenseByMonth(month, year)
    }
}