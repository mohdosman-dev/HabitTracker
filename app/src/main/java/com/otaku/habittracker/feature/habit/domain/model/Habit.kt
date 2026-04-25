package com.otaku.habittracker.feature.habit.domain.model

import java.time.ZonedDateTime

data class Habit(
    val id: Long = 0,
    val name: String,
    val icon: HabitIcon,
    val createdAt: ZonedDateTime,
    val frequency: HabitFrequency
)
