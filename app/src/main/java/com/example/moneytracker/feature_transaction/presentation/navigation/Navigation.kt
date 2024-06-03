package com.example.moneytracker.feature_transaction.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.moneytracker.feature_transaction.presentation.add_edit_category.components.AddEditCategory
import com.example.moneytracker.feature_transaction.presentation.add_edit_transaction.components.AddEditTransaction
import com.example.moneytracker.feature_transaction.presentation.categories.components.Categories
import com.example.moneytracker.feature_transaction.presentation.statistics.components.Statistics
import com.example.moneytracker.feature_transaction.presentation.transactions.components.Transactions

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.Transactions.route
    ) {
        composable(route = Screen.Transactions.route) {
            Transactions(navController = navController)
        }
        composable(
            route = Screen.AddEditTransaction.route + "?id={id}",
            arguments = listOf(
                navArgument(
                    name = "id"
                ) {
                    type = NavType.IntType
                    defaultValue = -1
                }
            )
        ) {
            AddEditTransaction(navController = navController)
        }
        composable(route = Screen.Statistics.route) {
            Statistics(navController = navController)
        }
        composable(route = Screen.Categories.route) {
            Categories(navController = navController)
        }
        composable(route = Screen.AddEditCategory.route) {
            AddEditCategory(navController = navController)
        }
    }
}
