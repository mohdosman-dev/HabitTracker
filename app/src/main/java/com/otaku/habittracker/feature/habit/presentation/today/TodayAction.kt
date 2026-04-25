package com.otaku.habittracker.feature.habit.presentation.today

sealed interface TodayAction {
    data class OnToggleHabit(val habitId: Long) : TodayAction
    data class OnHabitDetailClick(val habitId: Long? = null) : TodayAction
    data object OnStatsClick : TodayAction
}
