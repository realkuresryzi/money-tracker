package com.example.moneytracker.feature_transaction.presentation.transactions.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.moneytracker.feature_transaction.domain.util.Constants
import com.example.moneytracker.feature_transaction.presentation.bottom_bar.BottomBar
import com.example.moneytracker.feature_transaction.presentation.navigation.Screen
import com.example.moneytracker.feature_transaction.presentation.shared.text.Headline
import com.example.moneytracker.feature_transaction.presentation.transactions.TransactionsEvent
import com.example.moneytracker.feature_transaction.presentation.transactions.TransactionsViewModel
import kotlinx.coroutines.launch

@Composable
fun Transactions(
    navController: NavController,
    viewModel: TransactionsViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()


    Scaffold(
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Screen.AddEditTransaction.route)
                },
                Modifier
                    .background(Color.Transparent)
                    .padding(bottom = 95.dp)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
            }
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Headline(text = "${Constants.BALANCE}: ${state.balance}")
                IconButton(
                    onClick = {
                        viewModel.onEvent(TransactionsEvent.ToggleFilterBar)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "Filters"
                    )
                }
            }
            AnimatedVisibility(
                visible = state.isFilterBarVisible,
                enter = fadeIn() + slideInVertically(),
                exit = fadeOut() + slideOutVertically()
            ) {
                FilterBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp),
                    transactionOrder = state.transactionOrder,
                    orderType = state.orderType,
                    isExpenseFilter = state.isExpenseFilter,
                    categoryFilter = state.categoryFilter,
                    categories = state.categories,
                    onFilterChange = { to, ot, ief, cf ->
                        viewModel.onEvent(TransactionsEvent.Filter(to, ot, ief, cf))
                    }
                )
            }
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize()
                    .padding(horizontal = 20.dp),
                contentPadding = PaddingValues(bottom = 60.dp)
            ) {
                items(state.transactions) { transaction ->
                    TransactionItem(
                        item = transaction,
                        modifier = Modifier
                            .clickable {
                                navController.navigate(
                                    Screen.AddEditTransaction.route + "?id=${transaction.id}"
                                )
                            },
                        onDeleteClick = {
                            viewModel.onEvent(TransactionsEvent.Delete(transaction))
                            scope.launch {
                                val result = snackbarHostState.showSnackbar(
                                    message = Constants.TRANSACTION_DELETED,
                                    actionLabel = Constants.UNDO,
                                    duration = SnackbarDuration.Short
                                )
                                if (result == SnackbarResult.ActionPerformed) {
                                    viewModel.onEvent(TransactionsEvent.Restore)
                                }
                            }
                        }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
            BottomBar(
                navController = navController,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}





