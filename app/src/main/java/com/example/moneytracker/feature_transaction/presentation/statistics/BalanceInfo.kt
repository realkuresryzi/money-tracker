package com.example.moneytracker.feature_transaction.presentation.statistics

class BalanceInfo(
    val income: Double,
    val expense: Double,
){
    fun getBalance(): Double {
        return income - expense
    }
}