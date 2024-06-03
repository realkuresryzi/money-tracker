package com.example.moneytracker.feature_transaction.presentation.statistics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moneytracker.feature_transaction.data.entity.Category
import com.example.moneytracker.feature_transaction.domain.service.ICategoryService
import com.example.moneytracker.feature_transaction.domain.service.ITransactionService
import com.example.moneytracker.feature_transaction.domain.util.OrderType
import com.example.moneytracker.feature_transaction.domain.util.TransactionOrder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject


// TODO finish viewmodel, get data from db
@HiltViewModel
class StatisticsViewModel @Inject constructor(
    private val transactionService: ITransactionService,
    private val categoryService: ICategoryService
) : ViewModel() {

    val balanceInfo = BalanceInfo(55.0, 4.0)
    val totalForCategoriesForMonth =
        mutableListOf(
            TotalForCategoryForMonth(Category(1, "Food", 1, true, 1), 100),
            TotalForCategoryForMonth(Category(2, "Transport", 1, true, 1), 200),
            TotalForCategoryForMonth(Category(3, "Entertainment", 1, true, 1), 300),
            TotalForCategoryForMonth(Category(4, "Health", 1, true, 1), 400),
        )

    var state: StatisticsState

    init {
        val currentDateTime = LocalDateTime.now()
        var balanceInfo = balanceInfo
        var stateTotalForCategoryForMonth = totalForCategoriesForMonth
        state = StatisticsState(
            balanceInfo,
            stateTotalForCategoryForMonth,
            currentDateTime
        )
        fetchExpenses()
        fetchIncomes()
        updateTotalForCategoriesForMonth()

    }

    fun updateTotalForCategoriesForMonth() {
        state.totalForCategoriesForMonth.clear()
        state.totalForCategoriesForMonth.add(TotalForCategoryForMonth(Category(5, "New Category", 1, true, 1), 500))

    }

    fun fetchIncomes() {
        viewModelScope.launch {
            try {
                val transactionsIncome = transactionService.getTransactions(
                    false,
                    null,
                    TransactionOrder.DATE,
                    OrderType.DESC
                )

                var incomes = 0.0

                transactionsIncome.collect {
                    it.forEach {
                        incomes += it.amount
                        state.balance.income = incomes
                    }

                }
            } catch (e: Exception) {
                // Handle any exceptions that might occur during the database call
                println("Error fetching data: ${e.message}")
            }
        }
    }

     fun fetchExpenses() {

         viewModelScope.launch {
             try {
                 val transactionsExpense = transactionService.getTransactions(
                     true,
                     null,
                     TransactionOrder.DATE,
                     OrderType.DESC)

                 var expenses = 0.0

                 transactionsExpense.collect{
                     it.forEach {
                         expenses += it.amount
                         state.balance.expense = expenses
                     }

                 }
             } catch (e: Exception) {
                 // Handle any exceptions that might occur during the database call
                 println("Exception happened")
                 println("Error fetching data: ${e.message}")
             }
         }
    }

    fun fetchCategoriesOfExpenses() {
        viewModelScope.launch {
            try {
                val categories = categoryService.getCategories(true)

                var totalForCategoryForMonthList = mutableListOf<TotalForCategoryForMonth>()

                categories.collect{ cat ->

                    cat.forEach { categoryViewModel ->
                        println("Category: $categoryViewModel")
                        totalForCategoryForMonthList.add(TotalForCategoryForMonth(Category(categoryViewModel.id, categoryViewModel.name, 1, categoryViewModel.isExpense, categoryViewModel.iconResourceId), 2))


                    }
                    println("List is ")
                    println(totalForCategoryForMonthList)

                }

                println("Total for categories")
                println(state.totalForCategoriesForMonth)
            } catch (e: Exception) {
                // Handle any exceptions that might occur during the database call
                println("Error fetching data: ${e.message}")
            }
        }
    }
}
