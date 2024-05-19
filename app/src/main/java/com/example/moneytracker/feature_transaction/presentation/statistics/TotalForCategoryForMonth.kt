package com.example.moneytracker.feature_transaction.presentation.statistics

import com.example.moneytracker.feature_transaction.data.entity.Category

data class TotalForCategoryForMonth(
    var category: Category,
    var total: Int
)