package com.otaku.habittracker.feature.habit.presentation.detail

sealed interface HabitDetailEvent {
    data object HabitSaved : HabitDetailEvent
    data object HabitDeleted : HabitDetailEvent
    data object NavigateBack : HabitDetailEvent
}
