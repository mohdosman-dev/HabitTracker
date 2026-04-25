package com.otaku.habittracker.feature.habit.presentation.today

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.otaku.habittracker.feature.habit.domain.repository.HabitLocalDataSource
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.ZonedDateTime

class TodayViewModel(
    private val repository: HabitLocalDataSource
) : ViewModel() {

    val state = repository.getHabitsWithStats()
        .map { habits ->
            TodayState(
                habits = habits,
                isLoading = false
            )
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            TodayState(isLoading = true)
        )

    private val _events = Channel<TodayEvent>()
    val events = _events.receiveAsFlow()

    fun onAction(action: TodayAction) {
        when (action) {
            is TodayAction.OnToggleHabit -> {
                viewModelScope.launch {
                    repository.toggleCompletion(action.habitId, ZonedDateTime.now())
                }
            }
            is TodayAction.OnHabitClick -> {
                viewModelScope.launch {
                    _events.send(TodayEvent.NavigateToEditHabit(action.habitId))
                }
            }
            TodayAction.OnAddHabitClick -> {
                viewModelScope.launch {
                    _events.send(TodayEvent.NavigateToCreateHabit)
                }
            }
            TodayAction.OnStatsClick -> {
                viewModelScope.launch {
                    _events.send(TodayEvent.NavigateToStats)
                }
            }
        }
    }
}
