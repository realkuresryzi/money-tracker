package com.example.moneytracker.feature_transaction.presentation.add_edit_transaction.components

import ImageUploader
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
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.moneytracker.feature_transaction.domain.util.Constants
import com.example.moneytracker.feature_transaction.presentation.add_edit_transaction.AddEditTransactionEvent
import com.example.moneytracker.feature_transaction.presentation.add_edit_transaction.AddEditTransactionViewModel
import com.example.moneytracker.feature_transaction.presentation.shared.input.CategoryChip
import com.example.moneytracker.feature_transaction.presentation.shared.input.CustomInputField
import com.example.moneytracker.feature_transaction.presentation.shared.input.NumericInputField
import com.example.moneytracker.feature_transaction.presentation.shared.text.Label
import kotlinx.coroutines.flow.collectLatest
import loadImage

@OptIn(ExperimentalMaterial3Api::class)
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

//    val scaffoldState = rememberBottomSheetScaffoldState()
//    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
//                is AddEditTransactionViewModel.UiEvent.ShowSnackbar -> {
//                    scaffoldState.snackbarHostState.showSnackbar(
//                        message = event.message
//                    )
//                }

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
                Modifier.background(MaterialTheme.colorScheme.primary)
            ) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = Constants.SAVE
                )
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding) //16.dp
                .fillMaxSize()
        ) {
            Label(text = Constants.EXPENSE)
            LazyRow {
                items(expenseCategories) { category ->
                    CategoryChip(
                        categoryModel = category,
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

            Label(text = Constants.INCOME)
            LazyRow {
                items(incomeCategories) { category ->
                    CategoryChip(
                        categoryModel = category,
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
//                items(incomeCategories) { category ->
//                    CategoryChip(
//                        categoryModel = category,
//                        isSelected = (category == selectedCategory),
//                        onCategorySelected = { viewModel.onEvent(AddEditTransactionEvent.SelectCategory(it)) }
//                    )
//                }
            }
            Spacer(modifier = Modifier.height(15.dp))

            CustomInputField(
                value = title.text,
                onValueChange = { viewModel.onEvent(AddEditTransactionEvent.EnteredTitle(it)) },
                label = Constants.TITLE,
                placeholder = Constants.TEXT_INPUT_PLACEHOLDER
            )
            Spacer(modifier = Modifier.height(15.dp))

            NumericInputField(
                value = amount.text,
                onValueChange = { viewModel.onEvent(AddEditTransactionEvent.EnteredAmount(it)) },
                label = Constants.AMOUNT,
                placeholder = Constants.NUMERIC_INPUT_PLACEHOLDER
            )
            Spacer(modifier = Modifier.height(15.dp))

            Row(
                Modifier.fillMaxWidth()
            ) {
                ImageUploader(
                    onImageSelected = { viewModel.onEvent(AddEditTransactionEvent.UploadImage(it)) }
                )
                Spacer(modifier = Modifier.weight(1f))
                imageUri?.let { uri ->
                    val imageBitmap = loadImage(uri)
                    imageBitmap?.let { bitmap ->
                        Box(
                            modifier = Modifier
                                .heightIn(max = 100.dp)
                                .weight(1f)
                        ) {
                            Image(
                                bitmap = bitmap,
                                contentDescription = "Uploaded Image",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    }
                }
            }
//            Spacer(modifier = Modifier.height(15.dp))
            /*
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End,
                        ) {
                            Button(
                                onClick = {
                                    selectedCategoryModel = null
                                    amount = ""
                                    description = ""
                                    imageUri = null
                                },
                                modifier = Modifier.padding(end = 8.dp)
                            ) {
                                Text(text = "Clear")
                            }
                            Button(
                                enabled = amount.text.isNotBlank()
                                        && (amount.text.toDoubleOrNull() ?: 0.0) > 0.0
                                        && selectedCategory != null
                                        && title.text.isNotBlank(),
                                onClick = {
                                    selectedCategoryModel = null
                                    amount = ""
                                    description = ""
                                    imageUri = null
                                }
                            ) {
                                Text(text = "Add")
                            }
                        }
            */
        }
    }
}
