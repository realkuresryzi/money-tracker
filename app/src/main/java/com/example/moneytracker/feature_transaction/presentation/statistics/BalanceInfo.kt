package com.example.moneytracker.feature_transaction.presentation.statistics

class BalanceInfo(
    val income: Int,
    val expense: Int,
){
    fun getBalance(): Int {
        return income - expense
    }
}