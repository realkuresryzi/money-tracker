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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.moneytracker.R
import com.example.moneytracker.feature_transaction.presentation.bottom_bar.BottomBar
import com.example.moneytracker.feature_transaction.presentation.categories.CategoriesEvent
import com.example.moneytracker.feature_transaction.presentation.categories.CategoriesViewModel
import com.example.moneytracker.feature_transaction.presentation.navigation.Screen
import com.example.moneytracker.feature_transaction.presentation.shared.input.CategoryChip
import com.example.moneytracker.feature_transaction.presentation.shared.text.Headline
import com.example.moneytracker.feature_transaction.presentation.shared.text.Label
import kotlinx.coroutines.launch

@Composable
fun Categories(
    navController: NavController,
    viewModel: CategoriesViewModel = hiltViewModel()
) {
    val expenseCategories = viewModel.expenseCategories.value
    val incomeCategories = viewModel.incomeCategories.value
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val undoString = stringResource(R.string.undo)
    val deletedString = stringResource(R.string.category_deleted)
    Scaffold(
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Screen.AddEditCategory.route)
                },
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                modifier = Modifier
                    .background(Color.Transparent)
                    .padding(bottom = 65.dp)
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
                    imageVector = Icons.Default.Delete,
                    contentDescription = stringResource(R.string.delete),
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
                            viewModel.onEvent(CategoriesEvent.Delete(category)) { resString ->
                                var dispString = deletedString
                                var actionLabel = undoString
                                if (resString != "") {
                                    dispString = resString
                                    actionLabel = ""
                                }
                                scope.launch {
                                    val result = snackbarHostState.showSnackbar(
                                        message = dispString,
                                        actionLabel = actionLabel,
                                        duration = SnackbarDuration.Short
                                    )
                                    if (result == SnackbarResult.ActionPerformed) {
                                        viewModel.onEvent(CategoriesEvent.Restore) {}
                                    }
                                }
                            }
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
                            viewModel.onEvent(CategoriesEvent.Delete(category)) { resString ->
                                var dispString = deletedString
                                var actionLabel = undoString
                                if (resString != "") {
                                    dispString = resString
                                    actionLabel = ""
                                }
                                scope.launch {
                                    val result = snackbarHostState.showSnackbar(
                                        message = dispString,
                                        actionLabel = actionLabel,
                                        duration = SnackbarDuration.Short
                                    )
                                    if (result == SnackbarResult.ActionPerformed) {
                                        viewModel.onEvent(CategoriesEvent.Restore) {}
                                    }
                                }
                            }
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