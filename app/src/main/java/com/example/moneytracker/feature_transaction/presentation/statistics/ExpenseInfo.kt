package com.example.moneytracker.feature_transaction.presentation.statistics

class ExpenseInfo(
    val income: Int,
    val expense: Int,
){
    fun getBalance(): Int {
        return income - expense
    }
    fun getIncome(): Int {
        return income
    }

    fun getExpense(): Int {
        return expense
    }
}