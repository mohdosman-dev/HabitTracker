package com.otaku.habittracker.feature.habit.presentation.stats

import com.otaku.habittracker.feature.habit.domain.model.HabitWithStats
import java.time.LocalDate

data class StatsState(
    val habits: List<HabitWithStats> = emptyList(),
    val overallCompletionPercent: Int = 0,
    val bestStreakOverall: Int = 0,
    val activeHabitsCount: Int = 0,
    val heatmapData: Map<LocalDate, Float> = emptyMap(),
    val isLoading: Boolean = false
)
