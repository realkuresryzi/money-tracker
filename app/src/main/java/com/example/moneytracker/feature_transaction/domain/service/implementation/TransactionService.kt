package com.example.moneytracker.feature_transaction.domain.service.implementation

import com.example.moneytracker.feature_transaction.data.entity.Transaction
import com.example.moneytracker.feature_transaction.data.repository.ITransactionRepository
import com.example.moneytracker.feature_transaction.domain.mapper.EntityMapper
import com.example.moneytracker.feature_transaction.domain.model.CategoryModel
import com.example.moneytracker.feature_transaction.domain.model.TransactionModel
import com.example.moneytracker.feature_transaction.domain.service.ITransactionService
import com.example.moneytracker.feature_transaction.domain.util.OrderType
import com.example.moneytracker.feature_transaction.domain.util.TransactionOrder

class TransactionService(
    private val repository: ITransactionRepository,
    private val transactionMapper: EntityMapper<Transaction, TransactionModel>
) : ITransactionService {
    override suspend fun getTransactions(
        isExpenseFilter: Boolean?,
        categoryFilter: CategoryModel?,
        order: TransactionOrder,
        orderType: OrderType
    ): List<TransactionModel> {
        return repository.getTransactions(
            isExpenseFilter,
            categoryFilter?.id,
            order.columnName,
            orderType == OrderType.ASC
        ).map { transaction ->
            transactionMapper.entityToModel(transaction)
        }
    }

    override suspend fun getTransaction(id: Int): TransactionModel {
        return transactionMapper.entityToModel(repository.getTransactionById(id))
    }

    // TODO add annotaion Throws
    override suspend fun insertTransaction(transaction: TransactionModel) {
        // TODO validation, also create invalid transaction exception in services package
        repository.insertTransaction(transactionMapper.modelToEntity(transaction))
    }

    override suspend fun deleteTransaction(transaction: TransactionModel) {
        repository.deleteTransaction(transactionMapper.modelToEntity(transaction))
    }

    override suspend fun getTotalIncomesByMonth(month: Int, year: Int): Double {
        return repository.getTotalIncomesByMonth(month, year)
    }

    override suspend fun getTotalExpensesByMonth(month: Int, year: Int): Double {
        return repository.getTotalExpensesByMonth(month, year)
    }
}