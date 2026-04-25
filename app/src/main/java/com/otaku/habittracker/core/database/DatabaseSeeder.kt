package com.otaku.habittracker.core.database

import com.otaku.habittracker.feature.habit.domain.model.Habit
import com.otaku.habittracker.feature.habit.domain.model.HabitFrequency
import com.otaku.habittracker.feature.habit.domain.model.HabitIcon
import com.otaku.habittracker.feature.habit.domain.repository.HabitLocalDataSource
import kotlinx.coroutines.flow.first
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZonedDateTime
import kotlin.random.Random

object DatabaseSeeder {
    suspend fun seedIfEmpty(repository: HabitLocalDataSource) {
        val existingHabits = repository.getHabits().first()
        if (existingHabits.isNotEmpty()) return

        val habits = listOf(
            Habit(
                name = "Morning Run",
                icon = HabitIcon.RUN,
                createdAt = ZonedDateTime.now().minusWeeks(6),
                frequency = HabitFrequency(true, true, true, true, true, true, true)
            ),
            Habit(
                name = "Read 30min",
                icon = HabitIcon.READ,
                createdAt = ZonedDateTime.now().minusWeeks(6),
                frequency = HabitFrequency(true, true, true, true, true, false, false)
            ),
            Habit(
                name = "Meditate",
                icon = HabitIcon.MEDITATE,
                createdAt = ZonedDateTime.now().minusWeeks(6),
                frequency = HabitFrequency(true, false, true, false, true, false, true)
            ),
            Habit(
                name = "Drink Water",
                icon = HabitIcon.WATER,
                createdAt = ZonedDateTime.now().minusWeeks(4),
                frequency = HabitFrequency(true, true, true, true, true, true, true)
            ),
            Habit(
                name = "Code 1hr",
                icon = HabitIcon.CODE,
                createdAt = ZonedDateTime.now().minusWeeks(5),
                frequency = HabitFrequency(true, true, true, true, true, true, true)
            )
        )

        habits.forEach { habit ->
            repository.insertHabit(habit)
        }

        // Fetch them back to get generated IDs
        val insertedHabits = repository.getHabits().first()
        val today = LocalDate.now()
        val sixWeeksAgo = today.minusWeeks(6)

        insertedHabits.forEach { habit ->
            var date = sixWeeksAgo
            while (!date.isAfter(today)) {
                // Randomly complete based on frequency
                val isScheduled = when (date.dayOfWeek.value) {
                    1 -> habit.frequency.monday
                    2 -> habit.frequency.tuesday
                    3 -> habit.frequency.wednesday
                    4 -> habit.frequency.thursday
                    5 -> habit.frequency.friday
                    6 -> habit.frequency.saturday
                    7 -> habit.frequency.sunday
                    else -> false
                }

                if (isScheduled && Random.nextFloat() > 0.2f) { // 80% completion rate
                    val dateTime = ZonedDateTime.of(date, LocalTime.NOON, ZoneId.systemDefault())
                    repository.toggleCompletion(habit.id, dateTime)
                }
                date = date.plusDays(1)
            }
        }
    }
}
