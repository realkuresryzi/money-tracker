package com.example.moneytracker.ui.Items

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.moneytracker.R
import com.example.moneytracker.data.Category
import com.example.moneytracker.data.Record
import com.example.moneytracker.ui.theme.MoneyTrackerTheme
import com.example.moneytracker.ui.theme.Purple40
import java.util.Date

class ItemsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater?,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(this.context).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.Default)
            setContent {
                MoneyTrackerTheme {
                    ItemsPreview()
                }

            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun ItemsPreview() {
    val category = Category("1", "sports", Color.Black, "", true)
    val items = listOf(
        Record("1", "Item 1", category, Date()),
        Record("2", "Item 2", category, Date()),
        Record("3", "Item 3", category, Date())
    )
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        BalanceHeader(balance = 18000, modifier = Modifier.fillMaxWidth())
        ItemsList(items, modifier = Modifier.weight(1f))
        BottomBar(onItemClick = {}, modifier = Modifier.fillMaxWidth())
    }
}

@Composable
fun BalanceHeader(balance: Number, modifier: Modifier) {
    Column(modifier = modifier) { // Apply the modifier to the Column
        Text(
            text = "Balance",
            color = Color.Black,
            fontSize = 25.sp,
            modifier = Modifier
                .padding(top = 40.dp)
                .padding(start = 30.dp)
        )
        Text(
            text = balance.toString(),
            color = Purple40,
            fontSize = 50.sp,
            modifier = Modifier
                .padding(start = 30.dp)
                .padding(bottom = 40.dp)
        )
    }
}


@Composable
fun ItemsList(items: List<Record>, modifier: Modifier) {
    Column(modifier = modifier) {
        items.forEach { item ->
            ItemRow(item = item, modifier = modifier)
        }
    }
}

@Composable
fun ItemRow(item: Record, modifier: Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 15.dp, horizontal = 40.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.cart),
            contentDescription = null,
            modifier = Modifier.padding(end = 20.dp)
        )
        Text(
            text = item.title,
            modifier = Modifier.padding(start = 8.dp)
        )
    }

}

@Composable
fun BottomBar(
    onItemClick: (BottomBarItem) -> Unit,
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
                onItemClick = onItemClick
            )
            BottomBarItem(
                item = BottomBarItem.Add,
                onItemClick = onItemClick
            )
            BottomBarItem(
                item = BottomBarItem.Statistics,
                onItemClick = onItemClick
            )
        }
    }
}

@Composable
fun BottomBarItem(
    item: BottomBarItem,
    onItemClick: (BottomBarItem) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(id = item.icon),
            contentDescription = item.label
        )
        Text(
            text = item.label
        )
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