package com.example.moneytracker.feature_transaction.presentation

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.moneytracker.feature_transaction.data.storage.DatabaseSeeder
import com.example.moneytracker.feature_transaction.data.storage.RoomDb
import com.example.moneytracker.feature_transaction.domain.util.MonthlyNotificationWorker
import com.example.moneytracker.feature_transaction.presentation.navigation.Navigation
import com.example.moneytracker.ui.theme.MoneyTrackerTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Calendar
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var roomDb: RoomDb

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val seedExecuted = sharedPreferences.getBoolean("seed_executed", false)

        if (!seedExecuted) {
            val databaseSeeder = DatabaseSeeder(roomDb)
            CoroutineScope(Dispatchers.IO).launch {
                databaseSeeder.seedDatabase()
                withContext(Dispatchers.Main) {
                    sharedPreferences.edit().putBoolean("seed_executed", true).apply()
                }
            }
        }

        setContent {
            MoneyTrackerTheme {
//                var hasNotificationPermission by remember {
//                    mutableStateOf(checkNotificationPermission())
//                }
//
//                val permissionLauncher = rememberLauncherForActivityResult(
//                    contract = ActivityResultContracts.RequestPermission()
//                ) { isGranted ->
//                    hasNotificationPermission = isGranted
//                }
//
//                LaunchedEffect(Unit) {
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU && !hasNotificationPermission) {
//                        permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
//                    }
//                }
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Navigation()
                }
            }
        }

//        scheduleRepeatingNotification()
    }

    private fun checkNotificationPermission(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        } else {
            true
        }
    }

    private fun scheduleMonthlyNotification() {
        val constraints = Constraints.Builder()
            .setRequiresBatteryNotLow(true)
            .build()

        val request = PeriodicWorkRequestBuilder<MonthlyNotificationWorker>(30, TimeUnit.DAYS)
            .setInitialDelay(calculateInitialDelay(), TimeUnit.MILLISECONDS)
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "MonthlyNotificationWork",
            ExistingPeriodicWorkPolicy.UPDATE,
            request
        )
    }

    private fun scheduleRepeatingNotification() {
        val constraints = Constraints.Builder()
            .setRequiresBatteryNotLow(true)
            .build()

        val request = PeriodicWorkRequestBuilder<MonthlyNotificationWorker>(1, TimeUnit.MINUTES)
            .setInitialDelay(10, TimeUnit.SECONDS)
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "MonthlyNotificationWork",
            ExistingPeriodicWorkPolicy.UPDATE,
            request
        )
    }

    private fun calculateInitialDelay(): Long {
        val now = Calendar.getInstance()
        val nextMonth = Calendar.getInstance().apply {
            add(Calendar.MONTH, 1)
            set(Calendar.DAY_OF_MONTH, 1)
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        return nextMonth.timeInMillis - now.timeInMillis
    }
}
