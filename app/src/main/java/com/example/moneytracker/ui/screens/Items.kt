package com.example.moneytracker.ui.screens

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.moneytracker.R
import com.example.moneytracker.data.Category
import com.example.moneytracker.data.Transaction
import com.example.moneytracker.ui.theme.MoneyTrackerTheme
import com.example.moneytracker.ui.theme.Purple40
import java.util.Date
import com.example.moneytracker.ui.BottomBar.BottomBar
import java.text.SimpleDateFormat
import java.util.Locale

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
                    //ItemsPreview()
                }

            }
        }
    }

}

@Composable
//@Preview(showBackground = true)
fun ItemsPreview(navController: NavController) {
    //val navController = rememberNavController()
    val category = Category(
        "1",
        "sports",
        Color(50, 100, 50),
        ImageVector.vectorResource(id = R.drawable.basketball),
        true
    )
    val category2 = Category(
        "2",
        "shopping",
        Color(100, 50, 50),
        ImageVector.vectorResource(id = R.drawable.cart),
        true
    )
    val category3 = Category(
        "3",
        "food",
        Color(50, 50, 100),
        ImageVector.vectorResource(id = R.drawable.food_fork_drink),
        true
    )
    val items = listOf(
        Transaction("1", "Groceries", 55.60, category2, Date(1652342400000)), // Date: 2022-05-11
        Transaction("2", "Restaurant Dinner", 30.25, category2, Date(1664169600000)), // Date: 2022-09-25
        Transaction("3", "Movie Tickets", 20.00, category, Date(1664169600000)), // Date: 2023-01-31
        Transaction("4", "Gasoline", 40.00, category, Date(1664169600000)), // Date: 2023-07-14
        Transaction("5", "Bookstore Purchase", 15.50, category3, Date(1698700800000)), // Date: 2024-01-25
        Transaction("6", "Clothing", 65.75, category2, Date(1710614400000)), // Date: 2024-05-15
        Transaction("7", "Electronics", 300.00, category, Date(1722403200000)), // Date: 2024-11-18
        Transaction("8", "Home Improvement", 120.00, category, Date(1734268800000)), // Date: 2025-05-05
        Transaction("9", "Concert Tickets", 75.00, category, Date(1746158400000)), // Date: 2025-11-03
        Transaction("10", "Fitness Membership", 50.00, category, Date(1758016000000)), // Date: 2026-05-02
        Transaction("11", "Travel Booking", 500.00, category, Date(1769878400000)), // Date: 2026-11-02
        Transaction("12", "Gift Shopping", 80.00, category, Date(1781736000000)), // Date: 2027-05-01
        Transaction("13", "Car Maintenance", 200.00, category, Date(1793664000000)), // Date: 2027-10-30
        Transaction("14", "Education Expenses", 150.00, category, Date(1805529600000)), // Date: 2028-04-28
        Transaction("15", "Healthcare Services", 90.00, category, Date(1817387200000)) // Date: 2028-10-27
    )

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        BalanceHeader(balance = 18000, modifier = Modifier.fillMaxWidth())
        ItemsList(items, modifier = Modifier.weight(1f))
        BottomBar(
            navController = navController,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun BalanceHeader(balance: Number, modifier: Modifier) {
    Column(modifier = modifier) {
        Text(
            text = "Balance",
            color = Color.Black,
            fontSize = 25.sp,
            modifier = Modifier
                .padding(top = 40.dp)
                .padding(start = 30.dp)
        )
        Text(
            text = "$balance €",
            color = Purple40,
            fontSize = 50.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(start = 30.dp)
                .padding(bottom = 40.dp)
        )
    }
}

@Composable
fun ItemsList(items: List<Transaction>, modifier: Modifier) {
    LazyColumn(modifier = modifier) {
        var date = ""
        items.forEach { item ->
            item {
                val newDate = formattedDate(item.createdAt)
                ItemRow(item = item, newDay = (newDate != date))
                date = newDate
            }
        }
    }
}


fun formattedDate(datestring: Date): String {
    val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
    return dateFormat.format(datestring)
}


@Composable
fun ItemRow(item: Transaction, newDay: Boolean) {
    if (newDay) {
        Row(
            modifier = Modifier.padding(start = 30.dp, top = 15.dp)
        ) {
            Text(
                text = formattedDate(item.createdAt)
            )
            Divider(
                color = Color.Gray,
                modifier = Modifier
                    .padding(start = 30.dp, end = 30.dp)
                    .align(Alignment.CenterVertically)
            )
        }
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 15.dp, horizontal = 40.dp)
    ) {
        Icon(
            item.category.icon,
            "",
            modifier = Modifier.padding(end = 20.dp),
            tint = item.category.color
        )
        Text(
            text = item.title,
            modifier = Modifier.padding(start = 8.dp)
        )
        Box(
            modifier = Modifier
                .weight(1f)
        ) {
            Text(
                text = item.amount.toString() + " €",
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 10.dp)
            )
        }
    }


}
