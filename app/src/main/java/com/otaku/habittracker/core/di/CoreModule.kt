package com.otaku.habittracker.core.di

import androidx.room.Room
import com.otaku.habittracker.core.database.HabitDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val coreModule = module {
    single {
        Room.databaseBuilder(
            androidApplication(),
            HabitDatabase::class.java,
            "habit_tracker.db"
        ).build()
    }
    single { get<HabitDatabase>().habitDao }
}
