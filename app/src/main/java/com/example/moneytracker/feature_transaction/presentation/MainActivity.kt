package com.example.moneytracker.feature_transaction.presentation

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

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val databaseSeeder = DatabaseSeeder(roomDb)
        CoroutineScope(Dispatchers.IO).launch {
            databaseSeeder.seedDatabase()
        }

        setContent {
            MoneyTrackerTheme {
                // A surface container using the 'background' color from the theme
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
