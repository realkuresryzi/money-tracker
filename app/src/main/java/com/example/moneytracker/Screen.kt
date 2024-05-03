package com.example.moneytracker

sealed class Screen(val route: String) {
    object add : Screen("add")
    object items : Screen("items")
    object statistics : Screen("stats")
}