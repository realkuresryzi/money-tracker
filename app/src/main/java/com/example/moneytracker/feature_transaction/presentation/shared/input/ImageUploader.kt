package com.example.moneytracker.feature_transaction.presentation.shared.input

import android.Manifest
import android.app.Activity
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.core.app.ActivityCompat
import com.example.moneytracker.R

@Composable
fun ImageUploader(onImageSelected: (Uri) -> Unit, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val activity = (context as? Activity)
    val showRationale = remember { mutableStateOf(false) }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { onImageSelected(it) }
    }
    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            imagePickerLauncher.launch("image/*")
        } else {
            Toast.makeText(
                context,
                context.getString(R.string.permission_denied),
                Toast.LENGTH_SHORT
            )
                .show()
        }
    }

    if (showRationale.value) {
        AlertDialog(
            onDismissRequest = { showRationale.value = false },
            title = { Text(stringResource(id = R.string.permission_required)) },
            text = { Text(stringResource(id = R.string.access_images)) },
            confirmButton = {
                TextButton(onClick = {
                    showRationale.value = false
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        permissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)
                    }
                }) {
                    Text(stringResource(id = R.string.ok_btn))
                }
            },
            dismissButton = {
                TextButton(onClick = { showRationale.value = false }) {
                    Text(stringResource(id = R.string.cancel_btn))
                }
            }
        )
    }
    Button(
        onClick = {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
                imagePickerLauncher.launch("image/*")
                return@Button
            }
            if (activity != null && ActivityCompat.shouldShowRequestPermissionRationale(
                    activity,
                    Manifest.permission.READ_MEDIA_IMAGES
                )
            ) {
                showRationale.value = true
            } else {
                permissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)

            }
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            contentColor = MaterialTheme.colorScheme.onSecondaryContainer
        ),
        modifier = modifier
    ) {
        Text(
            text = stringResource(R.string.select_image),
            style = MaterialTheme.typography.labelLarge
        )
    }
}

@Composable
fun loadImage(uri: Uri): ImageBitmap? {
    val context = LocalContext.current
    return remember(uri) {
        val contentResolver = context.contentResolver
        try {
            val inputStream = contentResolver.openInputStream(uri)
            val bitmap = BitmapFactory.decodeStream(inputStream)
            bitmap.asImageBitmap()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
