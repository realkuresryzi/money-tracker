package com.example.moneytracker.feature_transaction.presentation.add_edit_category.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.moneytracker.R
import com.example.moneytracker.feature_transaction.presentation.add_edit_category.AddEditCategoryEvent
import com.example.moneytracker.feature_transaction.presentation.add_edit_category.AddEditCategoryViewModel
import com.example.moneytracker.feature_transaction.presentation.navigation.Screen
import com.example.moneytracker.feature_transaction.presentation.shared.text.Headline
import com.example.moneytracker.feature_transaction.presentation.shared.text.Label

@Composable
fun AddEditCategory(
    navController: NavController,
    viewModel: AddEditCategoryViewModel = hiltViewModel()
) {
    var name by remember { mutableStateOf(TextFieldValue()) }
    var isIncome by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Headline(text = "Add category")
        Spacer(modifier = Modifier.height(15.dp))
        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text(text = "Name") },
            placeholder = { Text(text = "Enter name") },
            modifier = Modifier
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(35.dp))

        Label(text = "Icon")
        Spacer(modifier = Modifier.height(5.dp))
        Box(
            modifier = Modifier
                .size(56.dp)
                .clip(CircleShape)
                .background(Color.Gray)
                .padding(8.dp)
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.blur_radial),
                contentDescription = "category",
                modifier = Modifier
                    .size(40.dp)
                    .padding(4.dp),
                tint = Color.White
            )
        }

        Spacer(modifier = Modifier.height(15.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = false,
                onCheckedChange = { isChecked ->
                    isIncome = isChecked
                }
            )
            Text(text = "Is this an income category?")
        }

        //Label(text = "Color")
        // TODO color picker

        Spacer(modifier = Modifier.height(15.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Button(
                onClick = {
                    name = TextFieldValue()
                },
                modifier = Modifier.padding(end = 8.dp)
            ) {
                Text(text = "Clear")
            }
            Button(
                onClick = {
                    viewModel.onEvent(
                        AddEditCategoryEvent.Save(
                            name = name.text,
                            isExpense = !isIncome
                        )
                    )
                    navController.navigate(Screen.Categories.route)
                }
            ) {
                Text(text = "Add")
            }
        }
    }
}