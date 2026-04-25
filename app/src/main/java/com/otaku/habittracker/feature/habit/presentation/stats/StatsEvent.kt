package com.otaku.habittracker.feature.habit.presentation.stats

sealed interface StatsEvent {
    data object NavigateBack : StatsEvent
}
