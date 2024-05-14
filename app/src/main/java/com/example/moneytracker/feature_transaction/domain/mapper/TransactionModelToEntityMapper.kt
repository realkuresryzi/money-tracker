package com.example.moneytracker.feature_transaction.domain.mapper

import com.example.moneytracker.feature_transaction.data.entity.Transaction
import com.example.moneytracker.feature_transaction.domain.model.TransactionViewModel

class TransactionModelToEntityMapper : Mapper<TransactionViewModel, Transaction> {
    override fun map(source: TransactionViewModel): Transaction {
        return Transaction(
            id = source.id,
            title = source.title,
            amount = source.amount,
            categoryId = source.category.id,
            imageUri = source.imageUri,
        )
    }
}