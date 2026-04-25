package com.otaku.habittracker.feature.habit.domain.util

import com.otaku.habittracker.feature.habit.domain.model.Habit
import com.otaku.habittracker.feature.habit.domain.model.HabitCompletion
import com.otaku.habittracker.feature.habit.domain.model.HabitFrequency
import java.time.DayOfWeek
import java.time.LocalDate
import java.util.SortedSet

object StreakCalculator {

    fun calculateCurrentStreak(
        habit: Habit,
        completionDates: Set<LocalDate>,
        today: LocalDate = LocalDate.now()
    ): Int {
        var date = today
        var streak = 0
        val startDate = habit.createdAt.toLocalDate()

        while (!date.isBefore(startDate)) {
            val isScheduled = habit.frequency.isScheduled(date)
            val isCompleted = completionDates.contains(date)

            if (isScheduled) {
                if (isCompleted) {
                    streak++
                } else {
                    // If it's today and not completed yet, the streak isn't "broken" until tomorrow
                    if (date != today) {
                        break
                    }
                }
            }
            date = date.minusDays(1)
        }
        return streak
    }

    fun calculateBestStreak(
        habit: Habit,
        completionDates: Set<LocalDate>,
        today: LocalDate = LocalDate.now()
    ): Int {
        var date = habit.createdAt.toLocalDate()
        var bestStreak = 0
        var currentRunningStreak = 0

        while (!date.isAfter(today)) {
            val isScheduled = habit.frequency.isScheduled(date)
            val isCompleted = completionDates.contains(date)

            if (isScheduled) {
                if (isCompleted) {
                    currentRunningStreak++
                    if (currentRunningStreak > bestStreak) {
                        bestStreak = currentRunningStreak
                    }
                } else {
                    currentRunningStreak = 0
                }
            }
            date = date.plusDays(1)
        }
        return bestStreak
    }

    private fun HabitFrequency.isScheduled(date: LocalDate): Boolean {
        return when (date.dayOfWeek) {
            DayOfWeek.MONDAY -> monday
            DayOfWeek.TUESDAY -> tuesday
            DayOfWeek.WEDNESDAY -> wednesday
            DayOfWeek.THURSDAY -> thursday
            DayOfWeek.FRIDAY -> friday
            DayOfWeek.SATURDAY -> saturday
            DayOfWeek.SUNDAY -> sunday
            else -> false
        }
    }
}
