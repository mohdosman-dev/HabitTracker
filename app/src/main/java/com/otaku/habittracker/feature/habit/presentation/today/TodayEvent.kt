package com.otaku.habittracker.feature.habit.presentation.today

sealed interface TodayEvent {
    data class NavigateToEditHabit(val habitId: Long) : TodayEvent
    data object NavigateToCreateHabit : TodayEvent
    data object NavigateToStats : TodayEvent
}
