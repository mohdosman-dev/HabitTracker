package com.otaku.habittracker.feature.habit.presentation.detail

import com.otaku.habittracker.core.designsystem.components.DayOfWeek
import com.otaku.habittracker.feature.habit.domain.model.HabitIcon

sealed interface HabitDetailAction {
    data class OnNameChange(val name: String) : HabitDetailAction
    data class OnIconChange(val icon: HabitIcon) : HabitDetailAction
    data class OnDayToggle(val day: DayOfWeek) : HabitDetailAction
    data object OnSaveClick : HabitDetailAction
    data object OnDiscardClick : HabitDetailAction
    data object OnDeleteClick : HabitDetailAction
    data object OnDeleteConfirm : HabitDetailAction
    data object OnDeleteDismiss : HabitDetailAction
}
