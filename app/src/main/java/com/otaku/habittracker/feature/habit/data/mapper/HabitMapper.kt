package com.otaku.habittracker.feature.habit.data.mapper

import com.otaku.habittracker.feature.habit.data.entity.HabitCompletionEntity
import com.otaku.habittracker.feature.habit.data.entity.HabitEntity
import com.otaku.habittracker.feature.habit.domain.model.Habit
import com.otaku.habittracker.feature.habit.domain.model.HabitCompletion
import com.otaku.habittracker.feature.habit.domain.model.HabitFrequency
import com.otaku.habittracker.feature.habit.domain.model.HabitIcon
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime

fun HabitEntity.toHabit(): Habit {
    return Habit(
        id = id,
        name = name,
        icon = try { HabitIcon.valueOf(iconName) } catch (e: IllegalArgumentException) { HabitIcon.RUN },
        createdAt = Instant.ofEpochMilli(createdAtMillis).atZone(ZoneId.systemDefault()),
        frequency = HabitFrequency(
            monday = repeatMon,
            tuesday = repeatTue,
            wednesday = repeatWed,
            thursday = repeatThu,
            friday = repeatFri,
            saturday = repeatSat,
            sunday = repeatSun
        )
    )
}

fun Habit.toHabitEntity(): HabitEntity {
    return HabitEntity(
        id = id,
        name = name,
        iconName = icon.name,
        createdAtMillis = createdAt.toInstant().toEpochMilli(),
        repeatMon = frequency.monday,
        repeatTue = frequency.tuesday,
        repeatWed = frequency.wednesday,
        repeatThu = frequency.thursday,
        repeatFri = frequency.friday,
        repeatSat = frequency.saturday,
        repeatSun = frequency.sunday
    )
}

fun HabitCompletionEntity.toHabitCompletion(): HabitCompletion {
    return HabitCompletion(
        habitId = habitId,
        completedAt = Instant.ofEpochMilli(completedAtMillis).atZone(ZoneId.systemDefault())
    )
}
