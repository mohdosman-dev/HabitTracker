package com.otaku.habittracker.feature.habit.presentation.detail

import com.otaku.habittracker.core.domain.DataError

sealed interface HabitDetailEvent {
    data object HabitSaved : HabitDetailEvent
    data class HabitSavedError(val error: DataError) : HabitDetailEvent
    data object HabitDeleted : HabitDetailEvent
    data class HabitDeletedError(val error: DataError) : HabitDetailEvent
    data object NavigateBack : HabitDetailEvent
}
