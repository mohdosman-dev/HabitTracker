package com.otaku.habittracker

import android.app.Application
import com.otaku.habittracker.core.di.coreModule
import com.otaku.habittracker.feature.habit.di.habitDataModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class HabitTrackerApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@HabitTrackerApp)
            modules(coreModule, habitDataModule)
        }
    }
}
