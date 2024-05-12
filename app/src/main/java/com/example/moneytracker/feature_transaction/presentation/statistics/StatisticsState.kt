package com.example.moneytracker.feature_transaction.presentation.statistics

data class StatisticsState(
    val expenseInfo: ExpenseInfo = ExpenseInfo(0, 0),
    val categoryMonthTotal: Collection<CategoryMonthTotal> = emptyList<CategoryMonthTotal>()
) {


}
