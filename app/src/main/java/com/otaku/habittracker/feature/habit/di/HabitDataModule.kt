package com.otaku.habittracker.feature.habit.di

import com.otaku.habittracker.feature.habit.data.repository.RoomHabitDataSource
import com.otaku.habittracker.feature.habit.domain.repository.HabitLocalDataSource
import org.koin.dsl.module

val habitDataModule = module {
    single<HabitLocalDataSource> { RoomHabitDataSource(get()) }
}
