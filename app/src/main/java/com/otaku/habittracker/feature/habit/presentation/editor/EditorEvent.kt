package com.otaku.habittracker.feature.habit.presentation.editor

sealed interface EditorEvent {
    data object HabitSaved : EditorEvent
    data object HabitDeleted : EditorEvent
    data object NavigateBack : EditorEvent
}
