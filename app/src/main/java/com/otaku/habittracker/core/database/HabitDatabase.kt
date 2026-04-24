package com.otaku.habittracker.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.otaku.habittracker.feature.habit.data.dao.HabitDao
import com.otaku.habittracker.feature.habit.data.entity.HabitCompletionEntity
import com.otaku.habittracker.feature.habit.data.entity.HabitEntity

@Database(
    entities = [HabitEntity::class, HabitCompletionEntity::class],
    version = 1
)
abstract class HabitDatabase : RoomDatabase() {
    abstract val habitDao: HabitDao
}
