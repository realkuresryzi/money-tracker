package com.example.moneytracker.ui.screens

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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.moneytracker.R
import com.example.moneytracker.Screen
import com.example.moneytracker.data.Category
import com.example.moneytracker.ui.BottomBar.BottomBar
import com.example.moneytracker.ui.components.input.CategoryChip
import com.example.moneytracker.ui.components.input.CustomInputField
import com.example.moneytracker.ui.components.text.Headline
import com.example.moneytracker.ui.components.text.Label
import com.example.moneytracker.ui.components.input.NumericInputField
import loadImage


@Composable
fun AddTransaction(
    navController: NavController,
) {
    val expenseCategories = listOf(
        Category(
            "1",
            "work",
            Color.Green,
            ImageVector.vectorResource(id = R.drawable.cart),
            true
        ),
        Category(
            "2",
            "sport",
            Color.Blue,
            ImageVector.vectorResource(id = R.drawable.basketball),
            true
        ),
        Category(
            "3",
            "slots",
            Color.Red,
            ImageVector.vectorResource(id = R.drawable.home),
            true
        ),
        Category(
            "4",
            "food",
            Color.Magenta,
            ImageVector.vectorResource(id = R.drawable.food_fork_drink),
            true
        )
    )
    val incomeCategories = listOf(
        Category(
            "5",
            "salary",
            Color.Cyan,
            ImageVector.vectorResource(id = R.drawable.shopping),
            true
        ),
        Category(
            "6",
            "lottery",
            Color.Yellow,
            ImageVector.vectorResource(id = R.drawable.invoice_list),
            true
        )
    )
    var selectedCategory by remember { mutableStateOf<Category?>(null) }
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
                    category = category,
                    isSelected = (category == selectedCategory),
                    onCategorySelected = { selectedCategory = category }
                )
            }
        }

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


