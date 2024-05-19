package com.example.moneytracker.feature_transaction.presentation.categories.components


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.moneytracker.R
import com.example.moneytracker.feature_transaction.presentation.bottom_bar.BottomBar
import com.example.moneytracker.feature_transaction.presentation.categories.CategoriesViewModel
import com.example.moneytracker.feature_transaction.presentation.navigation.Screen
import com.example.moneytracker.feature_transaction.presentation.shared.input.CategoryChip
import com.example.moneytracker.feature_transaction.presentation.shared.text.Headline
import com.example.moneytracker.feature_transaction.presentation.shared.text.Label

@Composable
fun Categories(
    navController: NavController,
    viewModel: CategoriesViewModel = hiltViewModel()
) {
    val expenseCategories = viewModel.expenseCategories.value
    val incomeCategories = viewModel.incomeCategories.value
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Screen.AddEditCategory.route)
                },
                Modifier
                    .background(Color.Transparent)
                    .padding(bottom = 95.dp)
            ) {
                Icon(painterResource(id = R.drawable.plus), contentDescription = "Add")
            }
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxHeight()
        ) {
            Headline(
                text = stringResource(R.string.categories),
                modifier = Modifier.padding(top = 20.dp, start = 10.dp)
            )
            Row(modifier = Modifier.padding(top = 20.dp, bottom = 20.dp)) {
                Icon(
                    painterResource(id = R.drawable.pencil),
                    contentDescription = stringResource(R.string.edit),
                    modifier = Modifier.padding(start = 10.dp, end = 10.dp)
                )
                Text(text = stringResource(R.string.click_a_category))

            }
            Label(text = stringResource(R.string.expense), Modifier.padding(15.dp))
            LazyRow {
                items(expenseCategories) { category ->
                    CategoryChip(
                        categoryViewModel = category,
                        isSelected = false,
                        onCategorySelected = {
                            navController.navigate(
                                Screen.AddEditCategory.route + "?id=${category.id}"
                            )
                        }
                    )
                }
            }
            Label(text = stringResource(R.string.income), Modifier.padding(15.dp))
            LazyRow {
                items(incomeCategories) { category ->
                    CategoryChip(
                        categoryViewModel = category,
                        isSelected = false,
                        onCategorySelected = {
                            navController.navigate(
                                Screen.AddEditCategory.route + "?id=${category.id}"
                            )
                        }
                    )
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            BottomBar(
                navController = navController,
                modifier = Modifier.fillMaxWidth()
            )
        }

    }
}