package com.example.moneytracker.feature_transaction.presentation.shared.input

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.moneytracker.R
import com.example.moneytracker.feature_transaction.domain.model.CategoryViewModel

@Composable
fun CategoryChip(
    categoryViewModel: CategoryViewModel,
    isSelected: Boolean,
    onCategorySelected: () -> Unit,
    modifier: Modifier = Modifier
) {
    val onSelectedModifier = if (isSelected) Modifier
        .border(
            width = 2.dp,
            color = categoryViewModel.color,
            shape = CircleShape
        )
    else Modifier

    Box(
        modifier = modifier
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
                modifier = onSelectedModifier
                    .border(
                        width = 4.dp,
                        color = MaterialTheme.colorScheme.background,
                        shape = CircleShape
                    )
                    .size(60.dp)
                    .clip(CircleShape)
                    .background(categoryViewModel.color)
                    .padding(8.dp)
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(categoryViewModel.iconResourceId),
                    contentDescription = stringResource(R.string.category),
                    modifier = Modifier
                        .size(44.dp)
                        .padding(4.dp),
                    tint = MaterialTheme.colorScheme.background
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = categoryViewModel.name,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}
