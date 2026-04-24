package com.otaku.habittracker.feature.habit.data.mapper

import com.otaku.habittracker.feature.habit.data.entity.HabitCompletionEntity
import com.otaku.habittracker.feature.habit.data.entity.HabitEntity
import com.otaku.habittracker.feature.habit.domain.model.Habit
import com.otaku.habittracker.feature.habit.domain.model.HabitCompletion

fun HabitEntity.toHabit(): Habit {
    return Habit(
        id = id,
        name = name,
        iconId = iconId,
        createdAtMillis = createdAtMillis,
        repeatMon = repeatMon,
        repeatTue = repeatTue,
        repeatWed = repeatWed,
        repeatThu = repeatThu,
        repeatFri = repeatFri,
        repeatSat = repeatSat,
        repeatSun = repeatSun
    )
}

fun Habit.toHabitEntity(): HabitEntity {
    return HabitEntity(
        id = id,
        name = name,
        iconId = iconId,
        createdAtMillis = createdAtMillis,
        repeatMon = repeatMon,
        repeatTue = repeatTue,
        repeatWed = repeatWed,
        repeatThu = repeatThu,
        repeatFri = repeatFri,
        repeatSat = repeatSat,
        repeatSun = repeatSun
    )
}

fun HabitCompletionEntity.toHabitCompletion(): HabitCompletion {
    return HabitCompletion(
        habitId = habitId,
        completedAtMillis = completedAtMillis
    )
}
