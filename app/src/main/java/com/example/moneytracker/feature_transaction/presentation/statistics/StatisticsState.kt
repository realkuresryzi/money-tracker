package com.example.moneytracker.feature_transaction.presentation.statistics

import java.time.LocalDateTime

data class StatisticsState(
    var balance: BalanceInfo,
    var totalForCategoriesForMonth: MutableList<TotalForCategoryForMonth>,
    val currentDateTime: LocalDateTime,
)