import android.graphics.BitmapFactory
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.example.moneytracker.R

@Composable
fun ImageUploader(onImageSelected: (Uri) -> Unit, modifier: Modifier = Modifier) {
    val activityResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { onImageSelected(it) }
    }

    Button(
        onClick = { activityResultLauncher.launch("image/*") },
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
