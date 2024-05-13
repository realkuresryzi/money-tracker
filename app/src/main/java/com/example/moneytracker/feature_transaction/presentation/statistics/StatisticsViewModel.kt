package com.example.moneytracker.feature_transaction.presentation.statistics;

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import com.example.moneytracker.feature_transaction.data.entity.Category
import com.example.moneytracker.feature_transaction.domain.service.ICategoryService
import com.example.moneytracker.feature_transaction.domain.service.ITransactionService
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.YearMonth
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class StatisticsViewModel @Inject constructor(
        private val transactionService: ITransactionService,
        private val categoryService: ICategoryService
): ViewModel(){

        val balanceInfo = BalanceInfo(55.0, 4.0)
        val totalForCategoriesForMonth =
                listOf(TotalForCategoryForMonth(Category(1,"Food", 1, true, 1 ), 100),
                        TotalForCategoryForMonth(Category(2,"Transport", 1, true, 1 ), 200),
                        TotalForCategoryForMonth(Category(3,"Entertainment", 1, true, 1 ), 300),
                        TotalForCategoryForMonth(Category(4,"Health", 1, true, 1 ), 400),
                        TotalForCategoryForMonth(Category(5,"Other", 1, true, 1 ), 500)

                )

        var state: StatisticsState = StatisticsState(balanceInfo, totalForCategoriesForMonth, "January", "2022")

        init {
                val currentMonth = YearMonth.now().month
                val currentYear = YearMonth.now().year

                // todo enable init with these calls to database
//                viewModelScope.launch {
//                        var ctModel: CategoryModel = CategoryModel(1, "Food", Color.Red, true, 1)
//                        categoryService.insertCategory(ctModel)
//                        var date: Date = Date(2024, 1, 1)
//                        transactionService.insertTransaction(TransactionModel(2, "transaction", 100.0, ctModel, date))
//
//                        val totalExpensesByLastMonth = transactionService.getTotalExpensesByMonth(1, 2024)
//                        val totalIncomeByLastMonth = transactionService.getTotalIncomesByMonth(1, 2024)
//
//                        val balanceInfoInput = BalanceInfo(totalIncomeByLastMonth, totalExpensesByLastMonth)
//                        state.balance = balanceInfoInput
//                }
                //todo make call for totalForCategoriesForMonth




                state = StatisticsState(balanceInfo, totalForCategoriesForMonth, currentMonth.name, currentYear.toString())
        }
}
