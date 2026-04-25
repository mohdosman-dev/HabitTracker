package com.otaku.habittracker.feature.habit.presentation.today

sealed interface TodayEvent {
    data class NavigateToHabitDetail(val habitId: Long? = null) : TodayEvent
    data object NavigateToStats : TodayEvent
}
