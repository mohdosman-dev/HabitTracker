package com.otaku.habittracker.feature.habit.domain.model

data class HabitStats(
    val currentStreak: Int,
    val bestStreak: Int,
    val isCompletedToday: Boolean
)

data class HabitWithStats(
    val habit: Habit,
    val stats: HabitStats
)
