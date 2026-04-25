package com.otaku.habittracker.core.designsystem.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.otaku.habittracker.core.designsystem.theme.HabitTrackerTheme

@Composable
fun HabitCheckbox(
    isChecked: Boolean,
    onCheckedChange: () -> Unit,
    modifier: Modifier = Modifier
) {
    val backgroundColor by animateColorAsState(
        targetValue = if (isChecked) HabitTrackerTheme.colors.success else Color.Transparent,
        label = "backgroundColor"
    )
    val borderColor by animateColorAsState(
        targetValue = if (isChecked) Color.Transparent else MaterialTheme.colorScheme.outline,
        label = "borderColor"
    )
    val iconScale by animateFloatAsState(
        targetValue = if (isChecked) 1f else 0f,
        label = "iconScale"
    )

    Box(
        modifier = modifier
            .size(30.dp)
            .clip(CircleShape)
            .background(backgroundColor)
            .border(2.dp, borderColor, CircleShape)
            .clickable { onCheckedChange() },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Default.Check,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.background,
            modifier = Modifier
                .size(16.dp)
                .scale(iconScale)
        )
    }
}
