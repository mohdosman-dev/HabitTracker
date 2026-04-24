package com.otaku.habittracker.feature.habit.domain.model

data class HabitCompletion(
    val habitId: Long,
    val completedAtMillis: Long
)
