package com.example.moneytracker.feature_transaction.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.moneytracker.feature_transaction.presentation.add_edit_transaction.components.AddEditTransaction

import com.example.moneytracker.feature_transaction.presentation.transactions.components.Transactions
import com.example.moneytracker.ui.screens.StatisticsPreview

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
        // TODO uncomment when implemented
//        composable(route = Screen.Categories.route) {
//            Categories(navController = navController)
//        }
//        composable(route = Screen.AddEditCategory.route) {
//            AddEditCategory(navController = navController)
//        }
        composable(route = Screen.Statistics.route) {
            StatisticsPreview(navController = navController)
        }
    }
}
