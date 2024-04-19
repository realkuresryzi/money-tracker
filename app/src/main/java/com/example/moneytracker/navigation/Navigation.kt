package com.example.moneytracker.navigation

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.moneytracker.Screen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.add.route) {
        composable(route = Screen.add.route) {

        }
    }
}

@Composable
fun Add(navController: NavController) {
    Button (
        onClick = {
            navController.navigate(Screen.items.route)
        }
    ) {
        Text(text = "to items")
    }
}

@Composable
fun Items(navController: NavController) {
    Button (
        onClick = {
            navController.navigate(Screen.add.route)
        }
    ) {
        Text(text = "to add")
    }
}
