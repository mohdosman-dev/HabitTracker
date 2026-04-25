package com.otaku.habittracker.feature.habit.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.otaku.habittracker.feature.habit.presentation.editor.EditorRoot
import com.otaku.habittracker.feature.habit.presentation.stats.StatsRoot
import com.otaku.habittracker.feature.habit.presentation.today.TodayRoot
import kotlinx.serialization.Serializable

@Serializable
data object TodayRoute

@Serializable
data object StatsRoute

@Serializable
data object CreateHabitRoute

@Serializable
data class EditHabitRoute(val habitId: Long)

fun NavGraphBuilder.habitGraph(
    navController: NavController
) {
    composable<TodayRoute> {
        TodayRoot(
            onNavigateToCreate = { navController.navigate(CreateHabitRoute) },
            onNavigateToEdit = { navController.navigate(EditHabitRoute(it)) },
            onNavigateToStats = { navController.navigate(StatsRoute) }
        )
    }
    composable<StatsRoute> {
        StatsRoot(
            onNavigateBack = { navController.popBackStack() }
        )
    }
    composable<CreateHabitRoute> {
        EditorRoot(
            onNavigateBack = { navController.popBackStack() }
        )
    }
    composable<EditHabitRoute> {
        EditorRoot(
            onNavigateBack = { navController.popBackStack() }
        )
    }
}
