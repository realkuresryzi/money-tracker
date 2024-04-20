package com.example.moneytracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Surface
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.moneytracker.domain.models.TransactionCategory
import com.example.moneytracker.ui.screens.AddExpense
import com.example.moneytracker.ui.theme.MoneyTrackerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MoneyTrackerTheme {
                val categories = remember {
                    listOf(
                        TransactionCategory(
                            1,
                            "Food",
                            Icons.Default.Email,
                            Color.Blue
                        ),
                        TransactionCategory(
                            2,
                            "Transportation",
                            Icons.Default.ShoppingCart,
                            Color.Yellow
                        ),
                        TransactionCategory(
                            3,
                            "Entertainment",
                            Icons.Default.Home,
                            Color.Magenta
                        ),
                        TransactionCategory(
                            4,
                            "Education",
                            Icons.Default.AccountBox,
                            Color.Magenta
                        ),
                        TransactionCategory(
                            5,
                            "Gaming",
                            Icons.Default.PlayArrow,
                            Color.Magenta
                        ),
                        TransactionCategory(
                            6,
                            "Security",
                            Icons.Default.Lock,
                            Color.Magenta
                        )
                    )
                }

                val categories2 = remember {
                    listOf(
                        TransactionCategory(
                            4,
                            "Gift",
                            Icons.Default.Check,
                            Color.Cyan
                        ),
                        TransactionCategory(
                            5,
                            "Salary",
                            Icons.Default.ArrowBack,
                            Color.Green
                        ),
                    )
                }

                // Display the AddExpense screen with the list of categories
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    AddExpense(categories, categories2)
                }
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun AddExpensePreview() {
//    MoneyTrackerTheme {
////        val categories = listOf(
////            ExpenseCategory("Food", Icon., MaterialTheme.colors.primary),
////            ExpenseCategory(
////                "Transportation",
////                Icon.,
////                MaterialTheme.colors.secondary
////            ),
////            ExpenseCategory(
////                "Entertainment",
////                Icon.,
////                MaterialTheme.colors.primaryVariant
////            ),
////            // Add more categories as needed
////        )
//        AddExpense()
//    }
//}