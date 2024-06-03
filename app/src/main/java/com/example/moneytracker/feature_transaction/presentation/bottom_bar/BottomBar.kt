package com.example.moneytracker.feature_transaction.presentation.bottom_bar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.moneytracker.feature_transaction.presentation.navigation.Screen

@Composable
fun BottomBar(
    navController: NavController,
    modifier: Modifier
) {
    Column(modifier = modifier.background(color = MaterialTheme.colorScheme.secondaryContainer)) {
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            BottomBarItem(
                item = BottomBarItem.Items,
                onItemClick = { navController.navigate(Screen.Transactions.route) }
            )
            BottomBarItem(
                item = BottomBarItem.Statistics,
                onItemClick = { navController.navigate(Screen.Statistics.route) }
            )
            BottomBarItem(
                item = BottomBarItem.Categories,
                onItemClick = { navController.navigate(Screen.Categories.route) }
            )
        }
    }
}
