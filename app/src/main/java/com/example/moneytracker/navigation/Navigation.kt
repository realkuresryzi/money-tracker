package com.example.moneytracker.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.moneytracker.Screen
import com.example.moneytracker.ui.screens.EditTransaction
import com.example.moneytracker.ui.screens.AddTransaction
import com.example.moneytracker.ui.screens.Categories

import com.example.moneytracker.ui.screens.ItemsPreview
import com.example.moneytracker.ui.screens.StatisticsPreview

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.Items.route) {
        composable(route = Screen.AddTransaction.route) {
            AddTransaction(navController = navController)
        }
        composable(route = Screen.Items.route) {
            ItemsPreview(navController = navController)
        }
        composable(route = Screen.Statistics.route) {
            StatisticsPreview(navController = navController)
        }
        composable(route = Screen.Categories.route) {
            Categories(navController = navController)
        }
    }
}
