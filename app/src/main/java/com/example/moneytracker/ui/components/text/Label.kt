package com.example.moneytracker.ui.components.text

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Label(text: String) {
    Text(
        text = text,
        style = TextStyle(fontSize = 18.sp),
        modifier = Modifier.padding(15.dp)
    )
}
