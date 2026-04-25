package com.otaku.habittracker.feature.habit.data.mapper

import com.otaku.habittracker.R
import com.otaku.habittracker.feature.habit.domain.model.HabitIcon

/**
 * Maps [HabitIcon] domain enum to Android drawable resource IDs and vice-versa.
 * Note: Resource IDs can change across builds, but the enum names remain stable in the DB.
 */
fun HabitIcon.toDrawableResId(): Int {
    return when (this) {
        HabitIcon.RUN -> R.drawable.ic_run
        HabitIcon.READ -> R.drawable.ic_read
        HabitIcon.WATER -> R.drawable.ic_water
        HabitIcon.MEDITATE -> R.drawable.ic_meditate
        HabitIcon.SLEEP -> R.drawable.ic_sleep
        HabitIcon.CODE -> R.drawable.ic_code
        HabitIcon.MUSIC -> R.drawable.ic_music
        HabitIcon.COOK -> R.drawable.ic_cook
        HabitIcon.JOURNAL -> R.drawable.ic_journal
        HabitIcon.GYM -> R.drawable.ic_gym
        HabitIcon.YOGA -> R.drawable.ic_yoga
        HabitIcon.WALK -> R.drawable.ic_walk
        HabitIcon.CYCLE -> R.drawable.ic_cycle
        HabitIcon.STUDY -> R.drawable.ic_study
        HabitIcon.NO_PHONE -> R.drawable.ic_no_phone
        HabitIcon.VITAMINS -> R.drawable.ic_vitamins
        HabitIcon.LANGUAGE -> R.drawable.ic_language
        HabitIcon.GRATITUDE -> R.drawable.ic_gratitude
        HabitIcon.HEALTH -> R.drawable.ic_health
        HabitIcon.ORGANIZE -> R.drawable.ic_organize
    }
}

fun Int.toHabitIcon(): HabitIcon {
    return when (this) {
        R.drawable.ic_run -> HabitIcon.RUN
        R.drawable.ic_read -> HabitIcon.READ
        R.drawable.ic_water -> HabitIcon.WATER
        R.drawable.ic_meditate -> HabitIcon.MEDITATE
        R.drawable.ic_sleep -> HabitIcon.SLEEP
        R.drawable.ic_code -> HabitIcon.CODE
        R.drawable.ic_music -> HabitIcon.MUSIC
        R.drawable.ic_cook -> HabitIcon.COOK
        R.drawable.ic_journal -> HabitIcon.JOURNAL
        R.drawable.ic_gym -> HabitIcon.GYM
        R.drawable.ic_yoga -> HabitIcon.YOGA
        R.drawable.ic_walk -> HabitIcon.WALK
        R.drawable.ic_cycle -> HabitIcon.CYCLE
        R.drawable.ic_study -> HabitIcon.STUDY
        R.drawable.ic_no_phone -> HabitIcon.NO_PHONE
        R.drawable.ic_vitamins -> HabitIcon.VITAMINS
        R.drawable.ic_language -> HabitIcon.LANGUAGE
        R.drawable.ic_gratitude -> HabitIcon.GRATITUDE
        R.drawable.ic_health -> HabitIcon.HEALTH
        R.drawable.ic_organize -> HabitIcon.ORGANIZE
        else -> HabitIcon.RUN
    }
}
