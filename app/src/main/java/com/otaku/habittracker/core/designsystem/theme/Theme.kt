package com.otaku.habittracker.core.designsystem.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf

@Composable
fun HabitTrackerTheme(
    content: @Composable () -> Unit
) {
    val customColors = HabitTrackerColors()
    val customTypography = CustomTypography()

    CompositionLocalProvider(
        LocalHabitTrackerColors provides customColors,
        LocalHabitTrackerTypography provides customTypography
    ) {
        MaterialTheme(
            colorScheme = DarkColorScheme,
            typography = HabitTrackerTypography,
            content = content
        )
    }
}

val LocalHabitTrackerTypography = staticCompositionLocalOf { CustomTypography() }

object HabitTrackerTheme {
    val colors: HabitTrackerColors
        @Composable
        @ReadOnlyComposable
        get() = LocalHabitTrackerColors.current

    val typography: CustomTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalHabitTrackerTypography.current
}
