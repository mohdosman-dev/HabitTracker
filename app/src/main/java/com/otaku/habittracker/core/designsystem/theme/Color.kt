package com.otaku.habittracker.core.designsystem.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

// Brand Colors
val HabitTrackerPrimary = Color(0xFF6C63FF)
val HabitTrackerSecondary = Color(0xFFA855F7)
val HabitTrackerPrimaryLight = Color(0xFF8B7BFF)
val HabitTrackerPrimaryDark = Color(0xFF4A42CC)

// Backgrounds
val HabitTrackerBackground = Color(0xFF0F0F15)
val HabitTrackerSurface = Color(0xFF1A1A2E)
val HabitTrackerSurfaceElevated = Color(0xFF252540)
val HabitTrackerSurfaceBright = Color(0xFF2E2E4A)

// Semantic
val HabitTrackerSuccess = Color(0xFF34D399)
val HabitTrackerStreak = Color(0xFFF59E0B)
val HabitTrackerAccent = Color(0xFFF472B6)
val HabitTrackerDestructive = Color(0xFFEF4444)

// Text
val HabitTrackerTextPrimary = Color(0xFFFFFFFF)
val HabitTrackerTextSecondary = Color(0xFFB0B0C0)
val HabitTrackerTextTertiary = Color(0xFF666680)
val HabitTrackerBorder = Color(0xFF333350)

val DarkColorScheme = darkColorScheme(
    primary = HabitTrackerPrimary,
    onPrimary = Color.White,
    secondary = HabitTrackerSecondary,
    onSecondary = Color.White,
    background = HabitTrackerBackground,
    onBackground = HabitTrackerTextPrimary,
    surface = HabitTrackerSurface,
    onSurface = HabitTrackerTextPrimary,
    surfaceVariant = HabitTrackerSurfaceElevated,
    onSurfaceVariant = HabitTrackerTextSecondary,
    error = HabitTrackerDestructive,
    onError = Color.White,
    outline = HabitTrackerBorder
)

@Immutable
data class HabitTrackerColors(
    val surface: Color = HabitTrackerSurface,
    val surfaceElevated: Color = HabitTrackerSurfaceElevated,
    val surfaceBright: Color = HabitTrackerSurfaceBright,
    val success: Color = HabitTrackerSuccess,
    val streak: Color = HabitTrackerStreak,
    val accent: Color = HabitTrackerAccent,
    val destructive: Color = HabitTrackerDestructive,
    val textPrimary: Color = HabitTrackerTextPrimary,
    val textSecondary: Color = HabitTrackerTextSecondary,
    val textTertiary: Color = HabitTrackerTextTertiary,
    val border: Color = HabitTrackerBorder,
    val primaryLight: Color = HabitTrackerPrimaryLight,
    val primaryDark: Color = HabitTrackerPrimaryDark
)

val LocalHabitTrackerColors = staticCompositionLocalOf { HabitTrackerColors() }
