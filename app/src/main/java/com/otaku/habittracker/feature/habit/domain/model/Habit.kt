package com.otaku.habittracker.feature.habit.domain.model

data class Habit(
    val id: Long = 0,
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
