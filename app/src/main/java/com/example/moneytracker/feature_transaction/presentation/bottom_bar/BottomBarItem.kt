package com.example.moneytracker.feature_transaction.presentation.bottom_bar

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.moneytracker.R

@Composable
fun BottomBarItem(
    item: BottomBarItem,
    onItemClick: () -> Unit
) {
    Button(
        onClick = onItemClick,
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(id = item.icon),
                contentDescription = stringResource(item.stringResourceId),
                tint = Color.Black
            )
            Text(
                text = stringResource(item.stringResourceId),
                style = MaterialTheme.typography.labelLarge,
                color = Color.Black
            )
        }
    }
}

sealed class BottomBarItem(
    val icon: Int,
    val stringResourceId: Int
) {
    data object Items : BottomBarItem(R.drawable.invoice_list, R.string.transactions)
    data object Categories : BottomBarItem(R.drawable.shape, R.string.categories)
    data object Statistics : BottomBarItem(R.drawable.chart_box, R.string.statistics)
}