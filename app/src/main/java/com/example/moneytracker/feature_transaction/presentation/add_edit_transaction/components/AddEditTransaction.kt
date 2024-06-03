package com.example.moneytracker.feature_transaction.presentation.add_edit_transaction.components

import ImageUploader
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.moneytracker.R
import com.example.moneytracker.feature_transaction.presentation.add_edit_transaction.AddEditTransactionEvent
import com.example.moneytracker.feature_transaction.presentation.add_edit_transaction.AddEditTransactionViewModel
import com.example.moneytracker.feature_transaction.presentation.shared.input.CategoryChip
import com.example.moneytracker.feature_transaction.presentation.shared.input.CustomInputField
import com.example.moneytracker.feature_transaction.presentation.shared.input.NumericInputField
import com.example.moneytracker.feature_transaction.presentation.shared.text.Label
import kotlinx.coroutines.flow.collectLatest
import loadImage

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun AddEditTransaction(
    navController: NavController,
    viewModel: AddEditTransactionViewModel = hiltViewModel()
) {
    val title = viewModel.title.value
    val amount = viewModel.amount.value
    val selectedCategory = viewModel.category.value
    val imageUri = viewModel.imageUri.value
    val incomeCategories = viewModel.incomeCategories.value
    val expenseCategories = viewModel.expenseCategories.value

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is AddEditTransactionViewModel.UiEvent.SaveTransaction -> {
                    navController.navigateUp()
                }
            }
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.onEvent(AddEditTransactionEvent.Save) },
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                modifier = Modifier.background(Color.Transparent)
            ) {
                Icon(
                    imageVector = Icons.Default.Done,
                    contentDescription = stringResource(R.string.save)
                )
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            Label(text = stringResource(R.string.expense), Modifier.padding(15.dp))
            LazyRow {
                items(expenseCategories) { category ->
                    CategoryChip(
                        categoryViewModel = category,
                        isSelected = category == selectedCategory,
                        onCategorySelected = {
                            viewModel.onEvent(
                                AddEditTransactionEvent.SelectCategory(
                                    category
                                )
                            )
                        }
                    )
                }
            }
            Spacer(modifier = Modifier.height(15.dp))

            Label(text = stringResource(R.string.income), modifier = Modifier.padding(15.dp))
            LazyRow {
                items(incomeCategories) { category ->
                    CategoryChip(
                        categoryViewModel = category,
                        isSelected = category == selectedCategory,
                        onCategorySelected = {
                            viewModel.onEvent(
                                AddEditTransactionEvent.SelectCategory(
                                    category
                                )
                            )
                        }
                    )
                }
            }
            Spacer(modifier = Modifier.height(15.dp))

            CustomInputField(
                value = title,
                onValueChange = { viewModel.onEvent(AddEditTransactionEvent.EnteredTitle(it)) },
                label = stringResource(R.string.title),
                placeholder = stringResource(R.string.title_input_placeholder),
                modifier = Modifier.padding(horizontal = 15.dp)
            )
            Spacer(modifier = Modifier.height(15.dp))

            NumericInputField(
                value = amount,
                onValueChange = { viewModel.onEvent(AddEditTransactionEvent.EnteredAmount(it)) },
                label = stringResource(R.string.amount),
                placeholder = stringResource(R.string.amount_input_placeholder),
                modifier = Modifier.padding(horizontal = 15.dp)
            )
            Spacer(modifier = Modifier.height(15.dp))
            Row(
                Modifier.fillMaxWidth()
            ) {
                ImageUploader(
                    onImageSelected = { viewModel.onEvent(AddEditTransactionEvent.UploadImage(it)) },
                    modifier = Modifier.padding(horizontal = 15.dp)
                )
                Spacer(modifier = Modifier.weight(1f))
                imageUri?.let { uri ->
                    val imageBitmap = loadImage(uri)
                    imageBitmap?.let { bitmap ->
                        Box(
                            modifier = Modifier
                                .heightIn(max = 100.dp)
                                .weight(1f)
                                .padding(end = 10.dp)
                        ) {
                            Image(
                                bitmap = bitmap,
                                contentDescription = stringResource(R.string.image),
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    }
                }
            }
        }
    }
}
