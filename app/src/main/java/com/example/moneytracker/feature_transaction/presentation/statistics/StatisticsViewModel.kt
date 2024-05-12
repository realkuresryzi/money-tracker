package com.example.moneytracker.feature_transaction.presentation.statistics;

import androidx.lifecycle.ViewModel
import com.example.moneytracker.feature_transaction.data.entity.Category
import com.example.moneytracker.feature_transaction.domain.service.ICategoryService;
import com.example.moneytracker.feature_transaction.domain.service.ITransactionService;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
class StatisticsViewModel @Inject constructor(
        private val transactionService: ITransactionService,
        private val categoryService: ICategoryService
): ViewModel(){

        val balanceInfo = BalanceInfo(55, 4)
        val totalForCategoriesForMonth =
                listOf(TotalForCategoryForMonth(Category(1,"Food", 1, true, 1 ), 100),
                        TotalForCategoryForMonth(Category(2,"Transport", 1, true, 1 ), 200),
                        TotalForCategoryForMonth(Category(3,"Entertainment", 1, true, 1 ), 300),
                        TotalForCategoryForMonth(Category(4,"Health", 1, true, 1 ), 400),
                        TotalForCategoryForMonth(Category(5,"Other", 1, true, 1 ), 500)

                )
        val currentMonth: String = "January"
        val currentYear: String = "2021"
}
