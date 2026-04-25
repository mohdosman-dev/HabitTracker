package com.otaku.habittracker.core.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.otaku.habittracker.core.designsystem.theme.HabitTrackerTheme
import com.otaku.habittracker.feature.habit.domain.model.HabitFrequency

@Composable
fun DayPickerRow(
    frequency: HabitFrequency,
    onDayToggle: (DayOfWeek) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        DayPickerItem(
            dayName = "M",
            isSelected = frequency.monday,
            onClick = { onDayToggle(DayOfWeek.MONDAY) },
            modifier = Modifier.weight(1f)
        )
        DayPickerItem(
            dayName = "T",
            isSelected = frequency.tuesday,
            onClick = { onDayToggle(DayOfWeek.TUESDAY) },
            modifier = Modifier.weight(1f)
        )
        DayPickerItem(
            dayName = "W",
            isSelected = frequency.wednesday,
            onClick = { onDayToggle(DayOfWeek.WEDNESDAY) },
            modifier = Modifier.weight(1f)
        )
        DayPickerItem(
            dayName = "T",
            isSelected = frequency.thursday,
            onClick = { onDayToggle(DayOfWeek.THURSDAY) },
            modifier = Modifier.weight(1f)
        )
        DayPickerItem(
            dayName = "F",
            isSelected = frequency.friday,
            onClick = { onDayToggle(DayOfWeek.FRIDAY) },
            modifier = Modifier.weight(1f)
        )
        DayPickerItem(
            dayName = "S",
            isSelected = frequency.saturday,
            onClick = { onDayToggle(DayOfWeek.SATURDAY) },
            modifier = Modifier.weight(1f)
        )
        DayPickerItem(
            dayName = "S",
            isSelected = frequency.sunday,
            onClick = { onDayToggle(DayOfWeek.SUNDAY) },
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun DayPickerItem(
    dayName: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val backgroundColor = if (isSelected) HabitTrackerTheme.colors.surfaceBright else MaterialTheme.colorScheme.surface
    val borderColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline
    val textColor = if (isSelected) MaterialTheme.colorScheme.secondary else HabitTrackerTheme.colors.textTertiary
    val borderWidth = if (isSelected) 1.5.dp else 1.dp

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .background(backgroundColor)
            .border(borderWidth, borderColor, RoundedCornerShape(10.dp))
            .clickable { onClick() }
            .padding(vertical = 10.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = dayName,
            color = textColor,
            style = HabitTrackerTheme.typography.caption.copy(fontWeight = FontWeight.SemiBold)
        )
    }
}

enum class DayOfWeek {
    MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
}
