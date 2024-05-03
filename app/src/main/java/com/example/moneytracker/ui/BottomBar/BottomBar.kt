package com.example.moneytracker.ui.BottomBar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.moneytracker.R
import com.example.moneytracker.Screen

class BottomBar {
}

@Composable
fun BottomBar(
    navController: NavController,
    modifier: Modifier,
    color: Color = Color(220, 220, 240)
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
                onItemClick = { navController.navigate(Screen.items.route) }
            )
            BottomBarItem(
                item = BottomBarItem.Add,
                onItemClick = { navController.navigate(Screen.add.route) }
            )
            BottomBarItem(
                item = BottomBarItem.Statistics,
                onItemClick = { navController.navigate(Screen.statistics.route) }
            )
        }
    }
}

@Composable
fun BottomBarItem(
    item: BottomBarItem,
    onItemClick: () -> Unit
) {
    Button(
        onClick = onItemClick,
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
        modifier = Modifier
            .width(115.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(id = item.icon),
                contentDescription = item.label,
                tint = Color.Black
            )
            Text(
                text = item.label,
                color = Color.Black
            )
        }
    }
}

sealed class BottomBarItem(
    val icon: Int,
    val label: String
) {
    object Items : BottomBarItem(R.drawable.invoice_list, "Items")
    object Add : BottomBarItem(R.drawable.plus, "Add")
    object Statistics : BottomBarItem(R.drawable.chart_box, "Statistics")
}