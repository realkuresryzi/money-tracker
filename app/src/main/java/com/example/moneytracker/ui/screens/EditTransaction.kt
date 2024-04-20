package com.example.moneytracker.ui.screens

import ImageUploader
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.moneytracker.domain.models.Transaction
import com.example.moneytracker.domain.models.TransactionCategory
import com.example.moneytracker.ui.components.CategoryChip
import com.example.moneytracker.ui.components.CustomInputField
import com.example.moneytracker.ui.components.NumericInputField
import com.example.moneytracker.ui.components.shared.Headline
import com.example.moneytracker.ui.components.shared.Label
import loadImage
import java.util.UUID

@Composable
fun EditTransaction(
    transaction: Transaction,
    expenseCategories: List<TransactionCategory>,
    incomeCategories: List<TransactionCategory>
) {
    var selectedCategory by remember { mutableStateOf<TransactionCategory?>(transaction.category) }
    var amount by remember { mutableStateOf(transaction.amount.toString()) }
    var description by remember { mutableStateOf(transaction.description) }
    var imageUri by remember { mutableStateOf(transaction.imageUri) }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Headline(text = "Edit transaction")
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

        TextField(
            value = description,
            onValueChange = { description = it },
            label = { Text(text = "Description") },
            placeholder = { Text(text = "Enter description") },
            modifier = Modifier
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(15.dp))


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