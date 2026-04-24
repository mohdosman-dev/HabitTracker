package com.otaku.habittracker.feature.habit.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class HabitEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val iconId: String,
    val createdAtMillis: Long,
    val repeatMon: Boolean,
    val repeatTue: Boolean,
    val repeatWed: Boolean,
    val repeatThu: Boolean,
    val repeatFri: Boolean,
    val repeatSat: Boolean,
    val repeatSun: Boolean
)
