package com.otaku.habittracker.feature.habit.presentation.today

sealed interface TodayAction {
    data class OnToggleHabit(val habitId: Long) : TodayAction
    data class OnHabitClick(val habitId: Long) : TodayAction
    data object OnAddHabitClick : TodayAction
    data object OnStatsClick : TodayAction
}
