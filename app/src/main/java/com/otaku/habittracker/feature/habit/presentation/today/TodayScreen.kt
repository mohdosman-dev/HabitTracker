package com.otaku.habittracker.feature.habit.presentation.today

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.otaku.habittracker.R
import com.otaku.habittracker.core.designsystem.components.HabitCard
import com.otaku.habittracker.core.designsystem.components.HabitCheckbox
import com.otaku.habittracker.core.designsystem.components.HabitIconContainer
import com.otaku.habittracker.core.designsystem.components.HabitTopAppBar
import com.otaku.habittracker.core.designsystem.theme.HabitTrackerTheme
import com.otaku.habittracker.core.presentation.ObserveAsEvents
import com.otaku.habittracker.feature.habit.domain.model.Habit
import com.otaku.habittracker.feature.habit.domain.model.HabitFrequency
import com.otaku.habittracker.feature.habit.domain.model.HabitIcon
import com.otaku.habittracker.feature.habit.domain.model.HabitStats
import com.otaku.habittracker.feature.habit.domain.model.HabitWithStats
import org.koin.androidx.compose.koinViewModel
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun TodayRoot(
    onNavigateToHabitDetail: (Long?) -> Unit,
    onNavigateToStats: () -> Unit,
    viewModel: TodayViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            is TodayEvent.NavigateToHabitDetail -> onNavigateToHabitDetail(event.habitId)
            TodayEvent.NavigateToStats -> onNavigateToStats()
        }
    }

    TodayScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@Composable
fun TodayScreen(
    state: TodayState,
    onAction: (TodayAction) -> Unit
) {
    val dateFormatter = DateTimeFormatter.ofPattern("EEEE, MMMM d", Locale.getDefault())
    val formattedDate = state.date.format(dateFormatter)

    Scaffold(
        topBar = {
            HabitTopAppBar(
                title = "Today",
                subtitle = formattedDate,
                actions = {
                    IconButton(
                        onClick = { onAction(TodayAction.OnStatsClick) },
                        modifier = Modifier
                            .size(44.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(HabitTrackerTheme.colors.surface)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_stats),
                            contentDescription = "Statistics",
                            tint = MaterialTheme.colorScheme.secondary,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onAction(TodayAction.OnHabitDetailClick()) },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = Color.White,
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.padding(bottom = 20.dp, end = 4.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Habit",
                    modifier = Modifier.size(24.dp)
                )
            }
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            DailyProgressSection(
                habits = state.habits,
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp)
            )

            if (state.habits.isEmpty()) {
                EmptyState(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f)
                )
            } else {
                HabitList(
                    habits = state.habits,
                    onAction = onAction,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun DailyProgressSection(
    habits: List<HabitWithStats>,
    modifier: Modifier = Modifier
) {
    val totalScheduled = habits.size
    val completed = habits.count { it.stats.isCompletedToday }
    val progress = if (totalScheduled > 0) completed.toFloat() / totalScheduled else 0f

    Column(modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Daily progress",
                style = MaterialTheme.typography.bodyMedium,
                color = HabitTrackerTheme.colors.textTertiary,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = "$completed / $totalScheduled",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.secondary,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(6.dp))
        LinearProgressIndicator(
            progress = progress,
            modifier = Modifier
                .fillMaxWidth()
                .height(5.dp)
                .clip(RoundedCornerShape(3.dp)),
            color = MaterialTheme.colorScheme.primary,
            trackColor = HabitTrackerTheme.colors.surface,
            strokeCap = StrokeCap.Round
        )
    }
}

@Composable
private fun HabitList(
    habits: List<HabitWithStats>,
    onAction: (TodayAction) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxWidth(),
        contentPadding = androidx.compose.foundation.layout.PaddingValues(
            start = 20.dp,
            end = 20.dp,
            top = 8.dp,
            bottom = 100.dp
        ),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(
            items = habits,
            key = { it.habit.id }
        ) { habitWithStats ->
            HabitItem(
                habitWithStats = habitWithStats,
                onToggle = { onAction(TodayAction.OnToggleHabit(habitWithStats.habit.id)) },
                onClick = { onAction(TodayAction.OnHabitDetailClick(habitWithStats.habit.id)) }
            )
        }
    }
}

@Composable
private fun HabitItem(
    habitWithStats: HabitWithStats,
    onToggle: () -> Unit,
    onClick: () -> Unit
) {
    HabitCard(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            HabitIconContainer(
                icon = habitWithStats.habit.icon,
                backgroundColor = HabitTrackerTheme.colors.surfaceElevated
            )

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = habitWithStats.habit.name,
                    style = HabitTrackerTheme.typography.titleLarge,
                    color = Color.White
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    if (habitWithStats.stats.currentStreak > 0) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            tint = HabitTrackerTheme.colors.streak,
                            modifier = Modifier.size(11.dp)
                        )
                        Text(
                            text = "${habitWithStats.stats.currentStreak} day streak",
                            style = MaterialTheme.typography.bodySmall,
                            color = HabitTrackerTheme.colors.streak,
                            fontSize = 11.sp
                        )
                    } else {
                        Text(
                            text = "No streak yet",
                            style = MaterialTheme.typography.bodySmall,
                            color = HabitTrackerTheme.colors.textTertiary,
                            fontSize = 11.sp
                        )
                    }
                }
            }

            HabitCheckbox(
                isChecked = habitWithStats.stats.isCompletedToday,
                onCheckedChange = onToggle
            )
        }
    }
}

@Composable
private fun EmptyState(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.padding(horizontal = 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "No habits yet",
            style = MaterialTheme.typography.titleLarge,
            color = HabitTrackerTheme.colors.textSecondary,
            fontWeight = FontWeight.SemiBold
        )
        Text(
            text = "Tap + to add your first habit",
            style = MaterialTheme.typography.bodyMedium,
            color = HabitTrackerTheme.colors.textTertiary,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}

@Preview
@Composable
private fun TodayScreenPreview() {
    HabitTrackerTheme {
        TodayScreen(
            state = TodayState(
                habits = listOf(
                    HabitWithStats(
                        habit = Habit(
                            id = 1,
                            name = "Morning Run",
                            icon = HabitIcon.RUN,
                            createdAt = ZonedDateTime.now(),
                            frequency = HabitFrequency(
                                monday = true, tuesday = true, wednesday = true,
                                thursday = true, friday = true, saturday = true, sunday = true
                            )
                        ),
                        stats = HabitStats(
                            currentStreak = 12,
                            bestStreak = 15,
                            isCompletedToday = true
                        )
                    ),
                    HabitWithStats(
                        habit = Habit(
                            id = 2,
                            name = "Meditate",
                            icon = HabitIcon.MEDITATE,
                            createdAt = ZonedDateTime.now(),
                            frequency = HabitFrequency(
                                monday = true, tuesday = true, wednesday = true,
                                thursday = true, friday = true, saturday = true, sunday = true
                            )
                        ),
                        stats = HabitStats(
                            currentStreak = 0,
                            bestStreak = 0,
                            isCompletedToday = false
                        )
                    )
                )
            ),
            onAction = {}
        )
    }
}
