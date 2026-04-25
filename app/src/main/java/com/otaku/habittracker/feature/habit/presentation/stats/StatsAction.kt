package com.otaku.habittracker.feature.habit.presentation.stats

sealed interface StatsAction {
    data object OnBackClick : StatsAction
}
