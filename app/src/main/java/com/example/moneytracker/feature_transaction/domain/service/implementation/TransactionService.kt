package com.example.moneytracker.feature_transaction.domain.service.implementation

import com.example.moneytracker.feature_transaction.data.entity.Transaction
import com.example.moneytracker.feature_transaction.data.repository.ITransactionRepository
import com.example.moneytracker.feature_transaction.domain.mapper.EntityMapper
import com.example.moneytracker.feature_transaction.domain.model.TransactionModel
import com.example.moneytracker.feature_transaction.domain.service.ITransactionService
import kotlinx.coroutines.flow.Flow

class TransactionService(
    private val repository: ITransactionRepository,
    private val transactionMapper: EntityMapper<Transaction, TransactionModel>
) : ITransactionService {
    override suspend fun getTransactions(
        isExpense: Boolean?,
        categoryId: Int?
    ): List<TransactionModel> {
        return repository.getTransactions(isExpense, categoryId).map { transaction ->
            transactionMapper.entityToModel(transaction)
        }
    }

    override suspend fun getTransaction(id: Int): TransactionModel {
        return transactionMapper.entityToModel(repository.getTransactionById(id))
    }

    override suspend fun insertTransaction(transaction: TransactionModel) {
        repository.insertTransaction(transactionMapper.modelToEntity(transaction))
    }

    override suspend fun deleteTransaction(transaction: TransactionModel) {
        repository.deleteTransaction(transactionMapper.modelToEntity(transaction))
    }
}