package com.otaku.habittracker.feature.habit.presentation.editor

import com.otaku.habittracker.core.designsystem.components.DayOfWeek
import com.otaku.habittracker.feature.habit.domain.model.HabitIcon

sealed interface EditorAction {
    data class OnNameChange(val name: String) : EditorAction
    data class OnIconChange(val icon: HabitIcon) : EditorAction
    data class OnDayToggle(val day: DayOfWeek) : EditorAction
    data object OnSaveClick : EditorAction
    data object OnDiscardClick : EditorAction
    data object OnDeleteClick : EditorAction
    data object OnDeleteConfirm : EditorAction
    data object OnDeleteDismiss : EditorAction
}
