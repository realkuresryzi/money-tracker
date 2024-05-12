package com.example.moneytracker.feature_transaction.presentation.statistics;

import androidx.compose.runtime.mutableStateOf
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
        var currentYear: Int = 2021
        var expenseInfo = ExpenseInfo(3000, 2000)

        var categoryMonthTotalList = mutableListOf<CategoryMonthTotal>()


        // get data for month from database


}
