package com.otaku.habittracker.feature.habit.domain.model

import java.time.ZonedDateTime

data class HabitCompletion(
    val habitId: Long,
    val completedAt: ZonedDateTime
)
