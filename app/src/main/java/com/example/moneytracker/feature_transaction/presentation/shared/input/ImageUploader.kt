import android.Manifest
import android.app.Activity
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
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

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
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
            Toast.makeText(context, "Permission denied. Cannot pick an image.", Toast.LENGTH_SHORT)
                .show()
        }
    }

    if (showRationale.value) {
        AlertDialog(
            onDismissRequest = { showRationale.value = false },
            title = { Text("Permission Required") },
            text = { Text("We need permission to access your media files to select an image.") },
            confirmButton = {
                TextButton(onClick = {
                    showRationale.value = false
                    permissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)
                }) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = { showRationale.value = false }) {
                    Text("Cancel")
                }
            }
        )
    }
    Button(
        onClick = {
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
