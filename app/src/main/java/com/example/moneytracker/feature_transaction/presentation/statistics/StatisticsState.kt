package com.example.moneytracker.feature_transaction.presentation.statistics

data class StatisticsState(
    val balance: BalanceInfo = BalanceInfo(0, 0),
    val totalForCategoriesForMonth: Collection<TotalForCategoryForMonth> = emptyList<TotalForCategoryForMonth>()
) {


}
