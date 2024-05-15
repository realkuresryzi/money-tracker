package com.example.moneytracker.feature_transaction.presentation.transactions.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.moneytracker.feature_transaction.domain.model.TransactionViewModel

@Composable
fun TransactionItem(
    item: TransactionViewModel,
    modifier: Modifier = Modifier,
    onDeleteClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
    ) {
        Icon(
            painter = painterResource(id = item.category.iconResourceId),
            contentDescription = null,
            tint = item.category.color,
            modifier = Modifier.padding(end = 10.dp)
        )
        val formattedAmount = if (item.amount % 1 == 0.0) item.amount.toInt().toString()
        else item.amount.toString()
        Text(
            text = if (item.category.isExpense) formattedAmount else "+$formattedAmount",
//            color = if (item.category.isExpense) Color.Red else Color.Green,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.End,
            modifier = Modifier
                .padding(end = 10.dp)
                .width(60.dp)
        )
        Column {
            Text(
                text = item.title,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
            Text(
                text = item.date,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(top = 4.dp)
            )
        }

        Spacer(Modifier.weight(1f))
        IconButton(
            onClick = onDeleteClick,
        ) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete"
            )
        }
    }
}
