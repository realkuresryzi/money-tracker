package com.example.moneytracker.feature_transaction.presentation.shared.others

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import loadImage

@Composable
fun ImageContainer(imageUri: Uri?, modifier: Modifier = Modifier) {
    imageUri?.let { uri ->
        val imageBitmap = loadImage(uri)
        imageBitmap?.let { bitmap ->
            Box(
                modifier = modifier
                    .heightIn(max = 100.dp)
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
