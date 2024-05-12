package com.example.moneytracker.feature_transaction.presentation.statistics;

import androidx.lifecycle.ViewModel
import com.example.moneytracker.feature_transaction.domain.service.ICategoryService;
import com.example.moneytracker.feature_transaction.domain.service.ITransactionService;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
class StatisticsViewModel @Inject constructor(
        private val transactionService: ITransactionService,
        private val categoryService: ICategoryService
): ViewModel(){
        var currentMonth: String = "August"
        private var currentYear: Int = 2021
        private var expenseInfo = ExpenseInfo(3000, 2000)


        // get data for month from database


        fun getCurrentMonth(): String {
                return this.currentMonth
        }

        fun getCurrentYear(): Int {
                return this.currentYear
        }

        fun getExpenseInfo(): ExpenseInfo {
                return this.expenseInfo
        }



}
