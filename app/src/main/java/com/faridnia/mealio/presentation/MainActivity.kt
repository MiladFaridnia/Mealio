package com.faridnia.mealio.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.faridnia.mealio.presentation.theme.MealioTheme
import com.faridnia.mealio.presentation.today.TodayScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MealioTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { _ ->
                    TodayScreen()
                }
            }
        }
    }
}