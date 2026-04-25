package com.otaku.habittracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.otaku.habittracker.core.database.DatabaseSeeder
import com.otaku.habittracker.core.designsystem.theme.HabitTrackerTheme
import com.otaku.habittracker.feature.habit.domain.repository.HabitLocalDataSource
import com.otaku.habittracker.feature.habit.presentation.navigation.TodayRoute
import com.otaku.habittracker.feature.habit.presentation.navigation.habitGraph
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {

    private val repository: HabitLocalDataSource by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launchWhenStarted {
            DatabaseSeeder.seedIfEmpty(repository)
        }

        enableEdgeToEdge()
        setContent {
            HabitTrackerTheme {
                val navController = rememberNavController()
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = TodayRoute,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        habitGraph(navController = navController)
                    }
                }
            }
        }
    }
}