package com.example.moneytracker.feature_transaction.presentation.statistics

import java.time.LocalDateTime

data class StatisticsState(
    var balance: BalanceInfo,
    val totalForCategoriesForMonth: Collection<TotalForCategoryForMonth> = emptyList<TotalForCategoryForMonth>(),
    val currentDateTime: LocalDateTime,
)