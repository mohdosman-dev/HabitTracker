package com.otaku.habittracker.feature.habit.presentation.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.otaku.habittracker.core.designsystem.components.DayOfWeek
import com.otaku.habittracker.core.domain.onSuccess
import com.otaku.habittracker.feature.habit.domain.model.Habit
import com.otaku.habittracker.feature.habit.domain.repository.HabitLocalDataSource
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.ZonedDateTime

class HabitDetailViewModel(
    private val repository: HabitLocalDataSource,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow(HabitDetailState())
    val state = _state.asStateFlow()

    private val _events = Channel<HabitDetailEvent>()
    val events = _events.receiveAsFlow()

    init {
        // SavedStateHandle should provide habitId if passed in route
        val habitId = savedStateHandle.get<Long>("habitId")
        if (habitId != null && habitId != -1L) {
            viewModelScope.launch {
                val habit = repository.getHabits().first().find { it.id == habitId }
                habit?.let { h ->
                    _state.update {
                        it.copy(
                            habitId = h.id,
                            name = h.name,
                            icon = h.icon,
                            frequency = h.frequency,
                            isIconPickerExpanded = false
                        )
                    }
                }
            }
        } else {
            _state.update { it.copy(isIconPickerExpanded = true) }
        }
    }

    fun onAction(action: HabitDetailAction) {
        when (action) {
            is HabitDetailAction.OnNameChange -> {
                _state.update { it.copy(name = action.name) }
            }
            is HabitDetailAction.OnIconChange -> {
                _state.update { it.copy(icon = action.icon) }
            }
            is HabitDetailAction.OnDayToggle -> {
                _state.update { 
                    it.copy(
                        frequency = it.frequency.toggleDay(action.day)
                    )
                }
            }
            HabitDetailAction.OnToggleIconPicker -> {
                _state.update { it.copy(isIconPickerExpanded = !it.isIconPickerExpanded) }
            }
            HabitDetailAction.OnSaveClick -> saveHabit()
            HabitDetailAction.OnDiscardClick -> {
                viewModelScope.launch { _events.send(HabitDetailEvent.NavigateBack) }
            }
            HabitDetailAction.OnDeleteClick -> {
                _state.update { it.copy(showDeleteDialog = true) }
            }
            HabitDetailAction.OnDeleteConfirm -> deleteHabit()
            HabitDetailAction.OnDeleteDismiss -> {
                _state.update { it.copy(showDeleteDialog = false) }
            }
        }
    }

    private fun saveHabit() {
        val currentState = _state.value
        if (!currentState.canSave) return

        viewModelScope.launch {
            _state.update { it.copy(isSaving = true) }
            val habit = Habit(
                id = currentState.habitId ?: 0,
                name = currentState.name,
                icon = currentState.icon,
                createdAt = ZonedDateTime.now(),
                frequency = currentState.frequency
            )
            repository.insertHabit(habit)
                .onSuccess {
                    _events.send(HabitDetailEvent.HabitSaved)
                }
            _state.update { it.copy(isSaving = false) }
        }
    }

    private fun deleteHabit() {
        val habitId = _state.value.habitId ?: return
        viewModelScope.launch {
            _state.update { it.copy(isDeleting = true, showDeleteDialog = false) }
            repository.deleteHabit(habitId)
                .onSuccess {
                    _events.send(HabitDetailEvent.HabitDeleted)
                }
            _state.update { it.copy(isDeleting = false) }
        }
    }

    private fun com.otaku.habittracker.feature.habit.domain.model.HabitFrequency.toggleDay(day: DayOfWeek): com.otaku.habittracker.feature.habit.domain.model.HabitFrequency {
        return when (day) {
            DayOfWeek.MONDAY -> copy(monday = !monday)
            DayOfWeek.TUESDAY -> copy(tuesday = !tuesday)
            DayOfWeek.WEDNESDAY -> copy(wednesday = !wednesday)
            DayOfWeek.THURSDAY -> copy(thursday = !thursday)
            DayOfWeek.FRIDAY -> copy(friday = !friday)
            DayOfWeek.SATURDAY -> copy(saturday = !saturday)
            DayOfWeek.SUNDAY -> copy(sunday = !sunday)
        }
    }
}
