package com.otaku.habittracker.feature.habit.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.otaku.habittracker.feature.habit.data.entity.HabitCompletionEntity
import com.otaku.habittracker.feature.habit.data.entity.HabitEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HabitDao {

    @Query("SELECT * FROM HabitEntity")
    fun getAllHabits(): Flow<List<HabitEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHabit(habit: HabitEntity): Long

    @Query("DELETE FROM HabitEntity WHERE id = :habitId")
    suspend fun deleteHabit(habitId: Long)

    @Query("SELECT * FROM HabitCompletionEntity WHERE habitId = :habitId")
    fun getCompletionsForHabit(habitId: Long): Flow<List<HabitCompletionEntity>>

    @Query("SELECT * FROM HabitCompletionEntity")
    fun getAllCompletions(): Flow<List<HabitCompletionEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCompletion(completion: HabitCompletionEntity)

    @Query("DELETE FROM HabitCompletionEntity WHERE habitId = :habitId AND completedAtMillis BETWEEN :startMillis AND :endMillis")
    suspend fun deleteCompletionForDay(habitId: Long, startMillis: Long, endMillis: Long)

    @Query("SELECT EXISTS(SELECT 1 FROM HabitCompletionEntity WHERE habitId = :habitId AND completedAtMillis BETWEEN :startMillis AND :endMillis)")
    suspend fun hasCompletionForDay(habitId: Long, startMillis: Long, endMillis: Long): Boolean
}
