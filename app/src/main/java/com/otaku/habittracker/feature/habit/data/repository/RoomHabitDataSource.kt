package com.otaku.habittracker.feature.habit.data.repository

import com.otaku.habittracker.core.domain.DataError
import com.otaku.habittracker.core.domain.EmptyResult
import com.otaku.habittracker.core.domain.Result
import com.otaku.habittracker.feature.habit.data.dao.HabitDao
import com.otaku.habittracker.feature.habit.data.entity.HabitCompletionEntity
import com.otaku.habittracker.feature.habit.data.mapper.toHabit
import com.otaku.habittracker.feature.habit.data.mapper.toHabitCompletion
import com.otaku.habittracker.feature.habit.data.mapper.toHabitEntity
import com.otaku.habittracker.feature.habit.domain.model.Habit
import com.otaku.habittracker.feature.habit.domain.model.HabitCompletion
import com.otaku.habittracker.feature.habit.domain.repository.HabitLocalDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZonedDateTime

class RoomHabitDataSource(
    private val habitDao: HabitDao
) : HabitLocalDataSource {

    override fun getHabits(): Flow<List<Habit>> {
        return habitDao.getAllHabits().map { entities ->
            entities.map { it.toHabit() }
        }
    }

    override fun getCompletions(habitId: Long): Flow<List<HabitCompletion>> {
        return habitDao.getCompletionsForHabit(habitId).map { entities ->
            entities.map { it.toHabitCompletion() }
        }
    }

    override fun getAllCompletions(): Flow<List<HabitCompletion>> {
        return habitDao.getAllCompletions().map { entities ->
            entities.map { it.toHabitCompletion() }
        }
    }

    override suspend fun insertHabit(habit: Habit): EmptyResult<DataError.Local> {
        return try {
            habitDao.insertHabit(habit.toHabitEntity())
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(DataError.Local.UNKNOWN)
        }
    }

    override suspend fun deleteHabit(habitId: Long): EmptyResult<DataError.Local> {
        return try {
            habitDao.deleteHabit(habitId)
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(DataError.Local.UNKNOWN)
        }
    }

    override suspend fun toggleCompletion(habitId: Long, dateTime: ZonedDateTime): EmptyResult<DataError.Local> {
        return try {
            val localDate = dateTime.toLocalDate()
            val startOfDay = localDate.atStartOfDay(ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli()
            val endOfDay = localDate.atTime(LocalTime.MAX)
                .atZone(ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli()

            val isCompleted = habitDao.hasCompletionForDay(habitId, startOfDay, endOfDay)
            if (isCompleted) {
                habitDao.deleteCompletionForDay(habitId, startOfDay, endOfDay)
            } else {
                habitDao.insertCompletion(
                    HabitCompletionEntity(
                        habitId = habitId,
                        completedAtMillis = dateTime.toInstant().toEpochMilli()
                    )
                )
            }
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(DataError.Local.UNKNOWN)
        }
    }
}
