package com.example.moneytracker.feature_transaction.domain.util

enum class TransactionOrder(val columnName: String) {
    TITLE(Constants.TITLE),
    AMOUNT(Constants.AMOUNT),
    DATE(Constants.DATE),
}