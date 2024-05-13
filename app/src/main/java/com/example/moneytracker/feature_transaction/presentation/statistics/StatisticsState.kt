package com.example.moneytracker.feature_transaction.presentation.statistics

data class StatisticsState(
    var balance: BalanceInfo,
    val totalForCategoriesForMonth: Collection<TotalForCategoryForMonth> = emptyList<TotalForCategoryForMonth>(),
    val currentMonth: String,
    val currentYear: String
) {


}
