package com.otaku.habittracker.feature.habit.presentation.stats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.otaku.habittracker.feature.habit.domain.model.Habit
import com.otaku.habittracker.feature.habit.domain.model.HabitCompletion
import com.otaku.habittracker.feature.habit.domain.model.HabitFrequency
import com.otaku.habittracker.feature.habit.domain.model.HabitWithStats
import com.otaku.habittracker.feature.habit.domain.repository.HabitLocalDataSource
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalDate

class StatsViewModel(
    private val repository: HabitLocalDataSource
) : ViewModel() {

    val state = combine(
        repository.getHabitsWithStats(),
        repository.getAllCompletions()
    ) { habits, completions ->
        val today = LocalDate.now()
        
        // 1. Overall Completion % for This Week
        val startOfWeek = today.with(DayOfWeek.MONDAY)
        val endOfWeek = today.with(DayOfWeek.SUNDAY)
        val thisWeekDays = (0..6).map { startOfWeek.plusDays(it.toLong()) }
        
        var totalScheduledSlots = 0
        var totalCompletionsThisWeek = 0
        
        habits.forEach { h ->
            thisWeekDays.forEach { day ->
                if (h.habit.frequency.isScheduled(day) && !day.isAfter(today)) {
                    totalScheduledSlots++
                    if (completions.any { it.habitId == h.habit.id && it.completedAt.toLocalDate() == day }) {
                        totalCompletionsThisWeek++
                    }
                }
            }
        }
        
        val completionPercent = if (totalScheduledSlots > 0) {
            (totalCompletionsThisWeek.toFloat() / totalScheduledSlots * 100).toInt()
        } else 0

        // 2. Best Streak Overall
        val bestStreakOverall = habits.maxOfOrNull { it.stats.bestStreak } ?: 0
        
        // 3. Active Habits Count
        val activeHabitsCount = habits.size

        // 4. Heatmap Data (last 4 weeks ending this Sunday)
        val heatmapData = mutableMapOf<LocalDate, Float>()
        val heatmapStart = endOfWeek.minusDays(27) // 4 weeks = 28 days
        
        (0..27).forEach { i ->
            val day = heatmapStart.plusDays(i.toLong())
            if (!day.isAfter(today)) {
                val scheduledForDay = habits.filter { it.habit.frequency.isScheduled(day) }
                if (scheduledForDay.isNotEmpty()) {
                    val completedOnDay = completions.count { it.completedAt.toLocalDate() == day }
                    heatmapData[day] = completedOnDay.toFloat() / scheduledForDay.size
                } else {
                    heatmapData[day] = 0f
                }
            }
        }

        StatsState(
            habits = habits,
            overallCompletionPercent = completionPercent,
            bestStreakOverall = bestStreakOverall,
            activeHabitsCount = activeHabitsCount,
            heatmapData = heatmapData
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        StatsState(isLoading = true)
    )

    private val _events = Channel<StatsEvent>()
    val events = _events.receiveAsFlow()

    fun onAction(action: StatsAction) {
        when (action) {
            StatsAction.OnBackClick -> {
                viewModelScope.launch { _events.send(StatsEvent.NavigateBack) }
            }
        }
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
