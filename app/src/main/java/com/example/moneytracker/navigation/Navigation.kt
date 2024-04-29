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

import com.example.moneytracker.ui.Items.ItemsPreview
import com.example.moneytracker.ui.Statistics.StatisticsPreview

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.add.route) {
        composable(route = Screen.add.route) {
            Add(navController = navController)
        }
        composable(route = Screen.items.route) {
            ItemsPreview(navController = navController)
        }
        composable(route = Screen.statistics.route) {
            StatisticsPreview(navController = navController)
        }
    }
}

//to be replaced with implementation of add screen
@Composable
fun Add(navController: NavController) {
    Column (
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = "adding")
        Button (
            onClick = {
                navController.navigate(Screen.items.route)
            }
        ) {
            Text(text = "to items")
        }
    }
}

//not in use, just to show how to work with navigation
@Composable
fun Items(navController: NavController) {
    Column (
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = "viewing items")
        Button (
            onClick = {
                navController.navigate(Screen.add.route)
            }
        ) {
            Text(text = "to add")
        }
    }
}
