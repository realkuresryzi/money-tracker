package com.example.moneytracker.feature_transaction.presentation.bottom_bar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.moneytracker.feature_transaction.presentation.navigation.Screen
import com.example.moneytracker.ui.theme.OffWhite

@Composable
fun BottomBar(
    navController: NavController,
    modifier: Modifier,
    color: Color = OffWhite
) {
    Column(modifier = modifier.background(color = color)) {
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
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
