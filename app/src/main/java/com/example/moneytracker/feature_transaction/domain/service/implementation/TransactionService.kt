package com.example.moneytracker.feature_transaction.domain.service.implementation

import com.example.moneytracker.feature_transaction.data.entity.Transaction
import com.example.moneytracker.feature_transaction.data.entity.TransactionWithCategory
import com.example.moneytracker.feature_transaction.data.repository.ITransactionRepository
import com.example.moneytracker.feature_transaction.domain.mapper.Mapper
import com.example.moneytracker.feature_transaction.domain.model.CategoryViewModel
import com.example.moneytracker.feature_transaction.domain.model.TransactionViewModel
import com.example.moneytracker.feature_transaction.domain.service.ITransactionService
import com.example.moneytracker.feature_transaction.domain.util.OrderType
import com.example.moneytracker.feature_transaction.domain.util.TransactionOrder

class TransactionService(
    private val repository: ITransactionRepository,
    private val transactionWithCategoryMapper: Mapper<TransactionWithCategory, TransactionViewModel>,
    private val transactionMapper: Mapper<TransactionViewModel, Transaction>
) : ITransactionService {
    override suspend fun getTransactions(
        isExpenseFilter: Boolean?,
        categoryFilter: CategoryViewModel?,
        order: TransactionOrder,
        orderType: OrderType
    ): List<TransactionViewModel> {
        return repository.getTransactions(
            isExpenseFilter,
            categoryFilter?.id,
            order.columnName,
            orderType == OrderType.ASC
        ).map { transaction ->
            transactionWithCategoryMapper.map(transaction)
        }
    }

    override suspend fun getTransaction(id: Int): TransactionViewModel {
        return transactionWithCategoryMapper.map(repository.getTransactionById(id))
    }

    override suspend fun insertTransaction(transaction: TransactionViewModel) {
        repository.insertTransaction(transactionMapper.map(transaction))
    }

    override suspend fun deleteTransaction(transaction: TransactionViewModel) {
        repository.deleteTransaction(transactionMapper.map(transaction))
    }

    override suspend fun getTotalIncomesByMonth(month: Int, year: Int): Double {
        return repository.getTotalIncomesByMonth(month, year)
    }

    override suspend fun getTotalExpensesByMonth(month: Int, year: Int): Double {
        return repository.getTotalExpensesByMonth(month, year)
    }
}