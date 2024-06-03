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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.math.BigDecimal
import java.math.RoundingMode

class TransactionService(
    private val repository: ITransactionRepository,
    private val entityToModelMapper: Mapper<TransactionWithCategory, TransactionViewModel>,
    private val modelToEntityMapper: Mapper<TransactionViewModel, Transaction>
) : ITransactionService {
    override fun getTransactions(
        isExpenseFilter: Boolean?,
        categoryFilter: CategoryViewModel?,
        order: TransactionOrder,
        orderType: OrderType
    ): Flow<List<TransactionViewModel>> {
        val transactionsList = repository.getTransactions(
            isExpenseFilter,
            categoryFilter?.id,
            order.columnName,
            orderType == OrderType.ASC
        )
        val transactionModels = transactionsList.map { transactions ->
            transactions.map { transaction ->
                entityToModelMapper.map(transaction)
            }
        }
        return transactionModels
    }

    override suspend fun getTransaction(id: Int): TransactionViewModel {
        return entityToModelMapper.map(repository.getTransactionById(id))
    }

    override suspend fun insertTransaction(transaction: TransactionViewModel) {
        val adjustedAmount =
            if ((transaction.category.isExpense && transaction.amount > 0)
                || (!transaction.category.isExpense && transaction.amount < 0)
            ) {
                -transaction.amount
            } else {
                transaction.amount
            }
        val roundedAmount = BigDecimal(adjustedAmount)
            .setScale(2, RoundingMode.HALF_EVEN)
            .toDouble()
        val validatedAmountTransaction = transaction.copy(amount = roundedAmount)
        repository.insertTransaction(modelToEntityMapper.map(validatedAmountTransaction))
    }

    override suspend fun deleteTransaction(transaction: TransactionViewModel) {
        repository.deleteTransaction(modelToEntityMapper.map(transaction))
    }

    override suspend fun getTotalIncomesByMonth(month: Int, year: Int): Double {
        return repository.getTotalIncomesByMonth(month, year)
    }

    override suspend fun getTotalExpensesByMonth(month: Int, year: Int): Double {
        return repository.getTotalExpensesByMonth(month, year)
    }
}