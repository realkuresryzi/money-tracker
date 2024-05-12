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
        private var income : Int = 30000
        private var expense : Int = 20000
        private var balance : Int = income - expense


        // get data for month from database


}
