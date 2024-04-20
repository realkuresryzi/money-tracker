package com.example.moneytracker.ui.screens

import ImageUploader
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.moneytracker.domain.models.TransactionCategory
import com.example.moneytracker.ui.components.CategoryChip
import com.example.moneytracker.ui.components.CustomInputField
import com.example.moneytracker.ui.components.shared.Headline
import com.example.moneytracker.ui.components.shared.Label
import com.example.moneytracker.ui.components.NumericInputField
import loadImage


@Composable
fun AddExpense(
    expenseCategories: List<TransactionCategory>,
    incomeCategories: List<TransactionCategory>
) {
    var selectedCategory by remember { mutableStateOf<TransactionCategory?>(null) }
    var amount by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    Column(
        modifier = Modifier
            .padding(15.dp)
            .fillMaxSize()
    ) {
        Headline(text = "Add transaction")
        Label(text = "Expense")
        LazyRow {
            items(expenseCategories) { category ->
                CategoryChip(
                    category = category,
                    isSelected = (category == selectedCategory),
                    onCategorySelected = { selectedCategory = category }
                )
            }
        }

        Spacer(modifier = Modifier.height(15.dp))

        Label(text = "Income")
        LazyRow {
            items(incomeCategories) { category ->
                CategoryChip(
                    category = category,
                    isSelected = (category == selectedCategory),
                    onCategorySelected = { selectedCategory = category }
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
            Modifier.fillMaxWidth()
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
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
        ) {
            Button(
                onClick = {
                    selectedCategory = null
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
                        && selectedCategory != null
                        && description.isNotBlank(),
                onClick = {
                    selectedCategory = null
                    amount = ""
                    description = ""
                    imageUri = null
                }
            ) {
                Text(text = "Add")
            }
        }
    }
}


