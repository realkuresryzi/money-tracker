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
import androidx.compose.material3.Divider
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.moneytracker.R
import com.example.moneytracker.feature_transaction.presentation.bottom_bar.BottomBar
import com.example.moneytracker.feature_transaction.presentation.navigation.Screen
import com.example.moneytracker.feature_transaction.presentation.shared.text.Headline
import com.example.moneytracker.feature_transaction.presentation.transactions.TransactionsEvent
import com.example.moneytracker.feature_transaction.presentation.transactions.TransactionsViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun Transactions(
    navController: NavController,
    viewModel: TransactionsViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val undoString = stringResource(R.string.undo)
    val deletedString = stringResource(R.string.transaction_deleted)

    val dayOnlyFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
    var prevDate = ""

    Scaffold(
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Screen.AddEditTransaction.route + "?id=0")
                },
                Modifier
                    .background(Color.Transparent)
                    .padding(bottom = 95.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.add)
                )
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
                Headline(text = "${stringResource(R.string.balance)}: ${state.balance} €")
                IconButton(
                    onClick = {
                        viewModel.onEvent(TransactionsEvent.ToggleFilterBar)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = stringResource(R.string.filters)
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
                    val curr = LocalDate.parse(transaction.date, dayOnlyFormatter).toString()
                    if (prevDate != curr) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)
                        ) {
                            Text(
                                text = curr,
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(end = 16.dp)
                            )
                            Divider(
                                color = MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier
                                    .height(1.dp)
                                    .weight(1f)
                            )
                        }
                    }
                    prevDate = curr
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
                                    message = deletedString,
                                    actionLabel = undoString,
                                    duration = SnackbarDuration.Short
                                )
                                if (result == SnackbarResult.ActionPerformed) {
                                    viewModel.onEvent(TransactionsEvent.Restore)
                                }
                            }
                        }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
            BottomBar(
                navController = navController,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}





