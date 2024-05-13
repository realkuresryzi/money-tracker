package com.example.moneytracker.feature_transaction.presentation.transactions.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.moneytracker.ui.theme.Purple40

@Composable
fun Header(balance: Number, modifier: Modifier) {
    // TODO either move this to presentation/shared/text
    // or use some of the existing components
    // i tried to use MaterialTheme styles and colors (see MaterialTheme.colorScheme
    // and MaterialTheme.typography)
    // so we have one common style in our app
    // it would be great if we replaced all Text composables,
    // so for displaying text we would only use custom composables
    // from shared folder

    // also we could add all hard coded string like this
    // text = "Balance"
    // into domain/util/constants so we would have all strings in one place
    Column(modifier = modifier) {
        Text(
            text = "Balance",
            color = Color.Black,
            fontSize = 25.sp,
            modifier = Modifier
                .padding(top = 40.dp)
                .padding(start = 30.dp)
        )
        Text(
            text = balance.toString() + " €",
            color = Purple40,
            fontSize = 50.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(start = 30.dp)
                .padding(bottom = 40.dp)
        )
    }
}