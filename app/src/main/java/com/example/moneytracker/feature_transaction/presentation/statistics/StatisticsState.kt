package com.example.moneytracker.feature_transaction.presentation.statistics

import java.time.LocalDateTime

data class StatisticsState(
    val balance: BalanceInfo,
    val totalForCategoriesForMonth: Collection<TotalForCategoryForMonth> = emptyList<TotalForCategoryForMonth>(),
    val currentDateTime: LocalDateTime,
)