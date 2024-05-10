package com.example.moneytracker

sealed class Screen(val route: String) {
    data object AddTransaction : Screen("addTransaction")
    data object EditTransaction : Screen("editTransaction")
    data object Items : Screen("items")
    data object Statistics : Screen("statistics")
    data object Categories : Screen("categories")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { append("/$it") }
        }
    }
}