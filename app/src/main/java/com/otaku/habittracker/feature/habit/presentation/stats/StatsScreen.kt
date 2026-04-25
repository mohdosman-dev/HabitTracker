package com.otaku.habittracker.feature.habit.presentation.stats

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.otaku.habittracker.R
import com.otaku.habittracker.core.designsystem.components.HabitIconContainer
import com.otaku.habittracker.core.designsystem.components.HabitTopAppBar
import com.otaku.habittracker.core.designsystem.theme.HabitTrackerTheme
import com.otaku.habittracker.core.presentation.ObserveAsEvents
import com.otaku.habittracker.feature.habit.domain.model.HabitWithStats
import org.koin.androidx.compose.koinViewModel
import java.time.DayOfWeek
import java.time.LocalDate

@Composable
fun StatsRoot(
    onNavigateBack: () -> Unit,
    viewModel: StatsViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            StatsEvent.NavigateBack -> onNavigateBack()
        }
    }

    StatsScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@Composable
fun StatsScreen(
    state: StatsState,
    onAction: (StatsAction) -> Unit
) {
    Scaffold(
        topBar = {
            HabitTopAppBar(
                title = "Statistics",
                navigationIcon = {
                    IconButton(
                        onClick = { onAction(StatsAction.OnBackClick) },
                        modifier = Modifier
                            .size(40.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(HabitTrackerTheme.colors.surface)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        if (state.habits.isEmpty() && !state.isLoading) {
            EmptyStatsState(modifier = Modifier.padding(padding).fillMaxSize())
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp, vertical = 8.dp)
            ) {
                SummaryCards(state)
                Spacer(modifier = Modifier.height(24.dp))
                HeatmapSection(state.heatmapData)
                Spacer(modifier = Modifier.height(24.dp))
                StreaksSection(state.habits)
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

@Composable
private fun SummaryCards(state: StatsState) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        SummaryCard(
            label = "This Week",
            value = "${state.overallCompletionPercent}%",
            valueColor = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.weight(1f)
        )
        SummaryCard(
            label = "Best Streak",
            value = state.bestStreakOverall.toString(),
            valueColor = HabitTrackerTheme.colors.success,
            modifier = Modifier.weight(1f)
        )
        SummaryCard(
            label = "Active",
            value = state.activeHabitsCount.toString(),
            valueColor = HabitTrackerTheme.colors.accent,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun SummaryCard(
    label: String,
    value: String,
    valueColor: Color,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(14.dp))
            .background(HabitTrackerTheme.colors.surface)
            .padding(16.dp, 12.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = HabitTrackerTheme.colors.textTertiary,
            fontSize = 10.sp
        )
        Text(
            text = value,
            style = HabitTrackerTheme.typography.displayLarge,
            color = valueColor,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}

@Composable
private fun HeatmapSection(heatmapData: Map<LocalDate, Float>) {
    Column {
        Text(
            text = "Activity",
            style = MaterialTheme.typography.titleLarge,
            color = Color.White,
            modifier = Modifier.padding(bottom = 10.dp)
        )
        
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(14.dp))
                .background(HabitTrackerTheme.colors.surface)
                .padding(16.dp)
        ) {
            // Day Labels (M-S)
            Row(
                modifier = Modifier
                    .padding(start = 32.dp, bottom = 6.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                listOf("M", "T", "W", "T", "F", "S", "S").forEach { day ->
                    Text(
                        text = day,
                        style = MaterialTheme.typography.labelSmall,
                        color = HabitTrackerTheme.colors.textTertiary,
                        modifier = Modifier.width(32.dp),
                        textAlign = TextAlign.Center,
                        fontSize = 9.sp
                    )
                }
            }

            // Grid Rows (W1-W4)
            val today = LocalDate.now()
            val endOfThisWeek = today.with(DayOfWeek.SUNDAY)
            val weeks = (0..3).map { weekIdx ->
                val weekStart = endOfThisWeek.minusDays(27).plusDays((weekIdx * 7).toLong())
                (0..6).map { dayIdx -> weekStart.plusDays(dayIdx.toLong()) }
            }

            weeks.forEachIndexed { weekIdx, days ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    modifier = Modifier.padding(vertical = 2.dp)
                ) {
                    Text(
                        text = "W${weekIdx + 1}",
                        style = MaterialTheme.typography.labelSmall,
                        color = HabitTrackerTheme.colors.textTertiary,
                        modifier = Modifier.width(28.dp),
                        textAlign = TextAlign.End,
                        fontSize = 9.sp
                    )
                    
                    days.forEach { day ->
                        val ratio = heatmapData[day] ?: 0f
                        val isFuture = day.isAfter(today)
                        val isToday = day == today
                        
                        HeatmapCell(
                            ratio = ratio,
                            isFuture = isFuture,
                            isToday = isToday
                        )
                    }
                }
            }

            // Legend
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Less", style = MaterialTheme.typography.labelSmall, color = HabitTrackerTheme.colors.textTertiary, fontSize = 9.sp)
                Spacer(modifier = Modifier.width(5.dp))
                listOf(0f, 0.25f, 0.5f, 0.75f, 1f).forEach { r ->
                    Box(
                        modifier = Modifier
                            .size(14.dp)
                            .clip(RoundedCornerShape(3.dp))
                            .background(if (r == 0f) HabitTrackerTheme.colors.surfaceElevated else MaterialTheme.colorScheme.primary.copy(alpha = r))
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                }
                Text(text = "More", style = MaterialTheme.typography.labelSmall, color = HabitTrackerTheme.colors.textTertiary, fontSize = 9.sp)
            }
        }
    }
}

@Composable
private fun HeatmapCell(
    ratio: Float,
    isFuture: Boolean,
    isToday: Boolean
) {
    val alpha = when {
        ratio <= 0f -> 1f
        ratio <= 0.25f -> 0.3f
        ratio <= 0.5f -> 0.6f
        ratio <= 0.75f -> 0.85f
        else -> 1f
    }
    
    val bgColor = if (ratio <= 0f) HabitTrackerTheme.colors.surfaceElevated else MaterialTheme.colorScheme.primary
    
    val borderModifier = when {
        isToday -> Modifier.border(2.dp, MaterialTheme.colorScheme.secondary, RoundedCornerShape(6.dp))
        isFuture -> Modifier.border(1.dp, HabitTrackerTheme.colors.border, RoundedCornerShape(6.dp))
        else -> Modifier
    }

    Box(
        modifier = Modifier
            .size(32.dp)
            .clip(RoundedCornerShape(6.dp))
            .then(borderModifier)
            .alpha(if (isFuture) 0.5f else 1f)
            .background(if (isFuture) MaterialTheme.colorScheme.surface else bgColor.copy(alpha = if (ratio <= 0f) 1f else alpha))
    )
}

@Composable
private fun StreaksSection(habits: List<HabitWithStats>) {
    Column {
        Text(
            text = "Streaks",
            style = MaterialTheme.typography.titleLarge,
            color = Color.White,
            modifier = Modifier.padding(bottom = 10.dp)
        )
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            habits.forEach { habit ->
                StreakItem(habit)
            }
        }
    }
}

@Composable
private fun StreakItem(habitWithStats: HabitWithStats) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(HabitTrackerTheme.colors.surface)
            .padding(14.dp, 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        HabitIconContainer(
            icon = habitWithStats.habit.icon,
            containerSize = 36.dp,
            iconSize = 18.dp
        )
        Text(
            text = habitWithStats.habit.name,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.White,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.weight(1f)
        )
        Column(horizontalAlignment = Alignment.End) {
            Text(
                text = habitWithStats.stats.currentStreak.toString(),
                style = MaterialTheme.typography.titleLarge,
                color = HabitTrackerTheme.colors.success,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Best: ${habitWithStats.stats.bestStreak}",
                style = MaterialTheme.typography.labelSmall,
                color = HabitTrackerTheme.colors.textTertiary,
                fontSize = 10.sp
            )
        }
    }
}

@Composable
private fun EmptyStatsState(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.padding(horizontal = 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Default.Info,
            contentDescription = null,
            tint = HabitTrackerTheme.colors.textTertiary,
            modifier = Modifier.size(64.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "No habits created yet",
            style = MaterialTheme.typography.titleLarge,
            color = HabitTrackerTheme.colors.textSecondary,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center
        )
    }
}

@Preview
@Composable
private fun StatsScreenPreview() {
    HabitTrackerTheme {
        StatsScreen(
            state = StatsState(),
            onAction = {}
        )
    }
}
