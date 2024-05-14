package com.example.moneytracker.feature_transaction.presentation.categories.components


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.moneytracker.feature_transaction.presentation.bottom_bar.BottomBar
import com.example.moneytracker.feature_transaction.presentation.navigation.Screen
import com.example.moneytracker.feature_transaction.presentation.shared.text.ErrorMessage
import com.example.moneytracker.feature_transaction.presentation.shared.text.Headline
import com.example.moneytracker.feature_transaction.presentation.shared.text.Label

@Composable
fun Categories(
    navController: NavController
    // TODO uncomment when viewmodel is created
//    viewModel: CategoryViewModel = hiltViewModel()
) {
    Scaffold(
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
        Column(modifier = Modifier.padding(innerPadding)) {
            Headline(text = "Categories")
            Label(text = "Choose a category")
            ErrorMessage(errorMessage = "ups")
            Spacer(modifier = Modifier.weight(1f))
            BottomBar(
                navController = navController,
                modifier = Modifier.fillMaxWidth()
            )
        }

    }
}