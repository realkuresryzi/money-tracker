package com.example.moneytracker.feature_transaction.presentation.statistics

import androidx.compose.runtime.sourceInformation
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moneytracker.feature_transaction.data.entity.Category
import com.example.moneytracker.feature_transaction.domain.service.ICategoryService
import com.example.moneytracker.feature_transaction.domain.service.ITransactionService
import com.example.moneytracker.feature_transaction.domain.util.OrderType
import com.example.moneytracker.feature_transaction.domain.util.TransactionOrder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.filter
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
        listOf(
            TotalForCategoryForMonth(Category(1, "Food", 1, true, 1), 100),
            TotalForCategoryForMonth(Category(2, "Transport", 1, true, 1), 200),
            TotalForCategoryForMonth(Category(3, "Entertainment", 1, true, 1), 300),
            TotalForCategoryForMonth(Category(4, "Health", 1, true, 1), 400),
            TotalForCategoryForMonth(Category(5, "Other", 1, true, 1), 500)

        )

    var state: StatisticsState

    init {
        val currentDateTime = LocalDateTime.now()
        var balanceInfo = balanceInfo
        state = StatisticsState(
            balanceInfo,
            totalForCategoriesForMonth,
            currentDateTime
        )
        println("Fetching data")
        fetchExpenses()
        fetchIncomes()

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
                        println("Transaction: $it")
                        incomes += it.amount
                        println(incomes)
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
                         println("Transaction: $it")
                         expenses += it.amount
                         println(expenses)
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
}
