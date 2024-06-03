package com.example.moneytracker.feature_transaction.presentation

import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.moneytracker.feature_transaction.data.storage.DatabaseSeeder
import com.example.moneytracker.feature_transaction.data.storage.RoomDb
import com.example.moneytracker.feature_transaction.presentation.navigation.Navigation
import com.example.moneytracker.ui.theme.MoneyTrackerTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var roomDb: RoomDb

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val seedExecuted = sharedPreferences.getBoolean("seed_executed", false)

        if (!seedExecuted) {
            val databaseSeeder = DatabaseSeeder(roomDb)
            CoroutineScope(Dispatchers.IO).launch {
                databaseSeeder.seedDatabase()
            }
            sharedPreferences.edit().putBoolean("seed_executed", true).apply()
        }

        setContent {
            MoneyTrackerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Navigation()
                }
            }
        }
    }
}
