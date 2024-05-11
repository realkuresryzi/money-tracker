package com.example.moneytracker.feature_transaction.presentation.navigation

sealed class Screen(val route: String) {
    data object Transactions : Screen("transactions")
    data object AddEditTransaction : Screen("addEditTransaction")
    data object Categories : Screen("categories")
    data object AddEditCategory : Screen("addEditCategory")
    data object Statistics : Screen("statistics")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { append("/$it") }
        }
    }
}