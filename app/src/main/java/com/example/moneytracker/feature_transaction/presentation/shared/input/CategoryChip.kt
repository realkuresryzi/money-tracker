package com.example.moneytracker.feature_transaction.presentation.shared.input

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.moneytracker.feature_transaction.domain.model.CategoryModel

@Composable
fun CategoryChip(
    categoryModel: CategoryModel,
    isSelected: Boolean,
    onCategorySelected: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(80.dp)
            .clickable { onCategorySelected() }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .clickable { onCategorySelected() }
        ) {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
                    .background(categoryModel.color)
                    .padding(8.dp)
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(categoryModel.iconResourceId),
                    contentDescription = "category",
                    modifier = Modifier
                        .size(40.dp)
                        .padding(4.dp),
                    tint = if (isSelected) Color.Black else Color.White
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = categoryModel.name,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(horizontal = 4.dp)
            )
        }
    }
}