package com.otaku.habittracker.core.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.otaku.habittracker.core.designsystem.theme.HabitTrackerTheme
import com.otaku.habittracker.feature.habit.data.mapper.toDrawableResId
import com.otaku.habittracker.feature.habit.domain.model.HabitIcon

@Composable
fun HabitIconContainer(
    icon: HabitIcon,
    modifier: Modifier = Modifier,
    containerSize: Dp = 42.dp,
    iconSize: Dp = 22.dp,
    backgroundColor: Color = HabitTrackerTheme.colors.surfaceElevated,
    iconColor: Color = MaterialTheme.colorScheme.secondary
) {
    Box(
        modifier = modifier
            .size(containerSize)
            .clip(RoundedCornerShape(12.dp))
            .background(backgroundColor),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(id = icon.toDrawableResId()),
            contentDescription = null,
            tint = iconColor,
            modifier = Modifier.size(iconSize)
        )
    }
}
