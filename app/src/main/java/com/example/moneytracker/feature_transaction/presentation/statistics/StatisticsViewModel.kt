package com.example.moneytracker.feature_transaction.presentation.statistics;

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moneytracker.feature_transaction.data.entity.Category
import com.example.moneytracker.feature_transaction.domain.service.ICategoryService;
import com.example.moneytracker.feature_transaction.domain.service.ITransactionService;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.launch

@HiltViewModel
class StatisticsViewModel @Inject constructor(
        private val transactionService: ITransactionService,
        private val categoryService: ICategoryService
): ViewModel(){

}
