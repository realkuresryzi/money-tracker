package com.example.moneytracker.feature_transaction.presentation.statistics

class BalanceInfo(
    var income: Double,
    var expense: Double,
){
    fun getBalance(): Double {
        return income + expense
    }
}