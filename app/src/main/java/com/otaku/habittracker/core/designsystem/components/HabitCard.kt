package com.otaku.habittracker.core.designsystem.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.otaku.habittracker.core.designsystem.theme.HabitTrackerTheme

@Composable
fun HabitCard(
    modifier: Modifier = Modifier,
    backgroundColor: Color = HabitTrackerTheme.colors.surfaceElevated,
    content: @Composable ColumnScope.() -> Unit
) {
    Surface(
        modifier = modifier.clip(RoundedCornerShape(14.dp)),
        color = backgroundColor,
        shape = RoundedCornerShape(14.dp)
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            content = content
        )
    }
}
