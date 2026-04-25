package com.otaku.habittracker.feature.habit.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.otaku.habittracker.feature.habit.presentation.detail.HabitDetailRoot
import com.otaku.habittracker.feature.habit.presentation.stats.StatsRoot
import com.otaku.habittracker.feature.habit.presentation.today.TodayRoot
import kotlinx.serialization.Serializable

@Serializable
data object TodayRoute

@Serializable
data object StatsRoute

@Serializable
data class HabitDetailRoute(val habitId: Long? = null)

fun NavGraphBuilder.habitGraph(
    navController: NavController
) {
    composable<TodayRoute> {
        TodayRoot(
            onNavigateToHabitDetail = { navController.navigate(HabitDetailRoute(it)) },
            onNavigateToStats = { navController.navigate(StatsRoute) }
        )
    }
    composable<StatsRoute> {
        StatsRoot(
            onNavigateBack = { navController.popBackStack() }
        )
    }
    composable<HabitDetailRoute> {
        HabitDetailRoot(
            onNavigateBack = { navController.popBackStack() }
        )
    }
}
