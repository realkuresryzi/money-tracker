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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.moneytracker.R
import com.example.moneytracker.feature_transaction.presentation.add_edit_category.AddEditCategoryEvent
import com.example.moneytracker.feature_transaction.presentation.add_edit_category.AddEditCategoryViewModel
import com.example.moneytracker.feature_transaction.presentation.navigation.Screen
import com.example.moneytracker.feature_transaction.presentation.shared.input.CustomInputField
import com.example.moneytracker.feature_transaction.presentation.shared.text.Headline
import com.example.moneytracker.feature_transaction.presentation.shared.text.Label

@Composable
fun AddEditCategory(
    navController: NavController,
    viewModel: AddEditCategoryViewModel = hiltViewModel()
) {
    val name = viewModel.name.value
    var isIncome = false //viewModel.isIncome.value

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Headline(text = stringResource(R.string.add_category))
        Spacer(modifier = Modifier.height(15.dp))

        CustomInputField(
            value = name,
            onValueChange = { viewModel.onEvent(AddEditCategoryEvent.EnteredName(it)) },
            label = stringResource(R.string.name),
            placeholder = stringResource(R.string.name_input_placeholder),
            modifier = Modifier.padding(horizontal = 15.dp)
        )

        Spacer(modifier = Modifier.height(35.dp))

        Label(text = stringResource(R.string.icon))
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
                contentDescription = stringResource(R.string.category),
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
                checked = isIncome,
                onCheckedChange = { isChecked ->
                    isIncome = isChecked
                    //viewModel.onEvent(AddEditCategoryEvent.EnteredIsExpense(isIncome))
                }
            )
            Text(text = stringResource(R.string.is_income_category))
        }

        Spacer(modifier = Modifier.height(15.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Button(
                onClick = {
                    //name = InputFieldState("")
                },
                modifier = Modifier.padding(end = 8.dp)
            ) {
                Text(text = stringResource(R.string.clear))
            }
            Button(
                onClick = {
                    viewModel.onEvent(AddEditCategoryEvent.Save)
                    navController.navigate(Screen.Categories.route)
                }
            ) {
                if (viewModel.currentId == 0) {
                    Text(text = stringResource(R.string.add))
                } else {
                    Text(text = stringResource(R.string.edit))
                }
            }
        }
    }
}