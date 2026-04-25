package com.otaku.habittracker.feature.habit.presentation.today

import androidx.compose.runtime.Stable
import com.otaku.habittracker.core.presentation.UiText
import com.otaku.habittracker.feature.habit.domain.model.HabitWithStats
import java.time.LocalDate

@Stable
data class TodayState(
    val habits: List<HabitWithStats> = emptyList(),
    val isLoading: Boolean = false,
    val date: LocalDate = LocalDate.now(),
    val error: UiText? = null
)
