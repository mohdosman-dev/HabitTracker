package com.otaku.habittracker.feature.habit.presentation.detail

import com.otaku.habittracker.core.presentation.UiText
import com.otaku.habittracker.feature.habit.domain.model.HabitFrequency
import com.otaku.habittracker.feature.habit.domain.model.HabitIcon
import java.time.ZonedDateTime

data class HabitDetailState(
    val habitId: Long? = null,
    val name: String = "",
    val icon: HabitIcon = HabitIcon.RUN,
    val frequency: HabitFrequency = HabitFrequency(
        monday = true, tuesday = true, wednesday = true,
        thursday = true, friday = true, saturday = true, sunday = true
    ),
    val isSaving: Boolean = false,
    val isDeleting: Boolean = false,
    val isIconPickerExpanded: Boolean = false,
    val showDeleteDialog: Boolean = false,
    val error: UiText? = null,
    val createdAt: ZonedDateTime? = null,
) {
    val isEditing: Boolean = habitId != null
    val canSave: Boolean = name.isNotBlank() && 
            name.length <= 200 && 
            (frequency.monday || frequency.tuesday || frequency.wednesday || 
             frequency.thursday || frequency.friday || frequency.saturday || frequency.sunday)
}
