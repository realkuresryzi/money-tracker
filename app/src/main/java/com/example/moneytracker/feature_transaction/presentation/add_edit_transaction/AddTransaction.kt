package com.example.moneytracker.feature_transaction.presentation.add_edit_transaction

import ImageUploader
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.moneytracker.R
import com.example.moneytracker.feature_transaction.presentation.Screen
import com.example.moneytracker.feature_transaction.domain.model.CategoryModel
import com.example.moneytracker.feature_transaction.presentation.bottom_bar.BottomBar
import com.example.moneytracker.feature_transaction.presentation.shared.input.CategoryChip
import com.example.moneytracker.feature_transaction.presentation.shared.input.CustomInputField
import com.example.moneytracker.feature_transaction.presentation.shared.text.Label
import com.example.moneytracker.feature_transaction.presentation.shared.input.NumericInputField
import loadImage


@Composable
fun AddTransaction(
    navController: NavController,
) {
    val expenseCategories = listOf(
        CategoryModel(
            "1",
            "work",
            Color.Green,
            ImageVector.vectorResource(id = R.drawable.cart),
            true
        ),
        CategoryModel(
            "2",
            "sport",
            Color.Blue,
            ImageVector.vectorResource(id = R.drawable.basketball),
            true
        ),
        CategoryModel(
            "3",
            "slots",
            Color.Red,
            ImageVector.vectorResource(id = R.drawable.home),
            true
        ),
        CategoryModel(
            "4",
            "food",
            Color.Magenta,
            ImageVector.vectorResource(id = R.drawable.food_fork_drink),
            true
        )
    )
    val incomeCategories = listOf(
        CategoryModel(
            "5",
            "salary",
            Color.Cyan,
            ImageVector.vectorResource(id = R.drawable.shopping),
            true
        ),
        CategoryModel(
            "6",
            "lottery",
            Color.Yellow,
            ImageVector.vectorResource(id = R.drawable.invoice_list),
            true
        )
    )
    var selectedCategoryModel by remember { mutableStateOf<CategoryModel?>(null) }
    var amount by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Label(text = "Expense")
        LazyRow {
            items(expenseCategories) { category ->
                CategoryChip(
                    categoryModel = category,
                    isSelected = (category == selectedCategoryModel),
                    onCategorySelected = { selectedCategoryModel = category }
                )
            }
        }

        Label(text = "Income")
        LazyRow {
            items(incomeCategories) { category ->
                CategoryChip(
                    categoryModel = category,
                    isSelected = (category == selectedCategoryModel),
                    onCategorySelected = { selectedCategoryModel = category }
                )
            }
        }

        Spacer(modifier = Modifier.height(15.dp))

        NumericInputField(
            value = amount,
            onValueChange = { amount = it },
            label = "Amount",
            placeholder = "Enter value"
        )

        Spacer(modifier = Modifier.height(15.dp))

        CustomInputField(
            value = description,
            onValueChange = { description = it },
            label = "Description",
            placeholder = "Enter descriptions"
        )

        Spacer(modifier = Modifier.height(15.dp))

        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp)
        ) {
            ImageUploader(onImageSelected = { imageUri = it })
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
        Spacer(modifier = Modifier.weight(1f))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp),
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
                enabled = amount.isNotBlank()
                        && (amount.toDoubleOrNull() ?: 0.0) > 0.0
                        && selectedCategoryModel != null
                        && description.isNotBlank(),
                onClick = {
                    navController.navigate(Screen.Items.route)
                }
            ) {
                Text(text = "Add")
            }
        }
        BottomBar(
            navController = navController,
            modifier = Modifier.fillMaxWidth()
        )
    }
}


