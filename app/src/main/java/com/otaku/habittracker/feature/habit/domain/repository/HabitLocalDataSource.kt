package com.otaku.habittracker.feature.habit.domain.repository

import com.otaku.habittracker.core.domain.DataError
import com.otaku.habittracker.core.domain.EmptyResult
import com.otaku.habittracker.feature.habit.domain.model.Habit
import com.otaku.habittracker.feature.habit.domain.model.HabitCompletion
import kotlinx.coroutines.flow.Flow

interface HabitLocalDataSource {
    fun getHabits(): Flow<List<Habit>>
    fun getCompletions(habitId: Long): Flow<List<HabitCompletion>>
    fun getAllCompletions(): Flow<List<HabitCompletion>>
    suspend fun insertHabit(habit: Habit): EmptyResult<DataError.Local>
    suspend fun deleteHabit(habitId: Long): EmptyResult<DataError.Local>
    suspend fun toggleCompletion(habitId: Long, dateMillis: Long): EmptyResult<DataError.Local>
}
