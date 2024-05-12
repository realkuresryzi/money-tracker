package com.example.moneytracker.feature_transaction.presentation.statistics

import com.example.moneytracker.feature_transaction.data.entity.Category

class CategoryMonthTotal (
    var  category: Category,
    var  total: Int
){
    fun getCategory(): Category {
        return category
    }

    fun getTotal(): Int {
        return total
    }


}