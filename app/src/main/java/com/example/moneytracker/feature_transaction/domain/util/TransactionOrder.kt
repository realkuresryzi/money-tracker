package com.example.moneytracker.feature_transaction.domain.util

import com.example.moneytracker.R
import com.example.moneytracker.feature_transaction.data.util.Constants

enum class TransactionOrder(val resourceStringId: Int, val columnName: String) {
    TITLE(R.string.title, Constants.TITLE),
    AMOUNT(R.string.amount, Constants.AMOUNT),
    DATE(R.string.date, Constants.DATE),
}