package com.example.moneytracker.feature_transaction.presentation.transactions

import com.example.moneytracker.feature_transaction.data.entity.Transaction

data class State(
    val transactions: List<Transaction>
)