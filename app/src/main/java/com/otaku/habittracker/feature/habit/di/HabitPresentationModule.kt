package com.otaku.habittracker.feature.habit.di

import com.otaku.habittracker.feature.habit.presentation.editor.EditorViewModel
import com.otaku.habittracker.feature.habit.presentation.stats.StatsViewModel
import com.otaku.habittracker.feature.habit.presentation.today.TodayViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val habitPresentationModule = module {
    viewModelOf(::TodayViewModel)
    viewModelOf(::EditorViewModel)
    viewModelOf(::StatsViewModel)
}
