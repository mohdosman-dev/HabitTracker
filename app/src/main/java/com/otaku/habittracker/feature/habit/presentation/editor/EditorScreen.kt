package com.otaku.habittracker.feature.habit.presentation.editor

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.otaku.habittracker.core.designsystem.components.DayPickerRow
import com.otaku.habittracker.core.designsystem.components.HabitButton
import com.otaku.habittracker.core.designsystem.components.HabitTopAppBar
import com.otaku.habittracker.core.designsystem.theme.HabitTrackerTheme
import com.otaku.habittracker.core.presentation.ObserveAsEvents
import com.otaku.habittracker.feature.habit.data.mapper.toDrawableResId
import com.otaku.habittracker.feature.habit.domain.model.HabitIcon
import org.koin.androidx.compose.koinViewModel
import java.time.ZonedDateTime

@Composable
fun EditorRoot(
    onNavigateBack: () -> Unit,
    viewModel: EditorViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            EditorEvent.HabitSaved,
            EditorEvent.HabitDeleted,
            EditorEvent.NavigateBack -> onNavigateBack()
        }
    }

    EditorScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@Composable
fun EditorScreen(
    state: EditorState,
    onAction: (EditorAction) -> Unit
) {
    if (state.showDeleteDialog) {
        DeleteConfirmationDialog(
            onConfirm = { onAction(EditorAction.OnDeleteConfirm) },
            onDismiss = { onAction(EditorAction.OnDeleteDismiss) }
        )
    }

    Scaffold(
        topBar = {
            HabitTopAppBar(
                title = if (state.isEditing) "Edit Habit" else "New Habit",
                navigationIcon = {
                    IconButton(
                        onClick = { onAction(EditorAction.OnDiscardClick) },
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 8.dp)
        ) {
            // Icon Picker Area
            IconHeader(
                icon = state.icon,
                isEditing = state.isEditing
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Icon Grid
            IconPickerGrid(
                selectedIcon = state.icon,
                onIconSelected = { onAction(EditorAction.OnIconChange(it)) }
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Name Field
            SectionLabel("NAME")
            HabitNameField(
                name = state.name,
                onNameChange = { onAction(EditorAction.OnNameChange(it)) }
            )

            Spacer(modifier = Modifier.height(22.dp))

            // Frequency Picker
            SectionLabel("REPEAT ON")
            DayPickerRow(
                frequency = state.frequency,
                onDayToggle = { onAction(EditorAction.OnDayToggle(it)) }
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Save Button
            HabitButton(
                text = if (state.isEditing) "Save Changes" else "Save Habit",
                onClick = { onAction(EditorAction.OnSaveClick) },
                enabled = state.canSave,
                isLoading = state.isSaving
            )

            // Secondary Action
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = if (state.isEditing) "Delete Habit" else "Discard Habit",
                style = MaterialTheme.typography.bodyMedium,
                color = HabitTrackerTheme.colors.destructive,
                fontWeight = FontWeight.Medium,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { 
                        if (state.isEditing) {
                            onAction(EditorAction.OnDeleteClick)
                        } else {
                            onAction(EditorAction.OnDiscardClick)
                        }
                    }
                    .padding(vertical = 8.dp),
                textAlign = TextAlign.Center
            )
            
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun IconHeader(
    icon: HabitIcon,
    isEditing: Boolean
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(14.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        val borderModifier = if (isEditing) {
            Modifier.border(2.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(20.dp))
        } else {
            Modifier.border(2.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(20.dp))
        }

        Box(
            modifier = Modifier
                .size(72.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(HabitTrackerTheme.colors.surface)
                .then(borderModifier),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = icon.toDrawableResId()),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(28.dp)
            )
        }
        Text(
            text = "Tap to change icon",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
private fun IconPickerGrid(
    selectedIcon: HabitIcon,
    onIconSelected: (HabitIcon) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(HabitTrackerTheme.colors.surface)
            .padding(14.dp)
    ) {
        SectionLabel("CHOOSE ICON", modifier = Modifier.padding(bottom = 10.dp))
        
        val icons = HabitIcon.entries
        val rows = icons.chunked(5)
        
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            rows.forEach { rowIcons ->
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    rowIcons.forEach { icon ->
                        val isSelected = icon == selectedIcon
                        val backgroundColor = if (isSelected) MaterialTheme.colorScheme.primary else HabitTrackerTheme.colors.surfaceElevated
                        val iconColor = if (isSelected) Color.White else MaterialTheme.colorScheme.secondary
                        val borderModifier = if (isSelected) {
                            Modifier.border(2.dp, HabitTrackerTheme.colors.primaryLight, RoundedCornerShape(12.dp))
                        } else Modifier

                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .aspectRatio(1f)
                                .clip(RoundedCornerShape(12.dp))
                                .background(backgroundColor)
                                .then(borderModifier)
                                .clickable { onIconSelected(icon) },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painter = painterResource(id = icon.toDrawableResId()),
                                contentDescription = null,
                                tint = iconColor,
                                modifier = Modifier.size(22.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun HabitNameField(
    name: String,
    onNameChange: (String) -> Unit
) {
    BasicTextField(
        value = name,
        onValueChange = onNameChange,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(HabitTrackerTheme.colors.surface)
            .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(12.dp))
            .padding(14.dp, 16.dp),
        textStyle = MaterialTheme.typography.bodyLarge.copy(color = Color.White),
        cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
        singleLine = true,
        decorationBox = { innerTextField ->
            if (name.isEmpty()) {
                Text(
                    text = "Habit name...",
                    style = MaterialTheme.typography.bodyLarge,
                    color = HabitTrackerTheme.colors.textTertiary
                )
            }
            innerTextField()
        }
    )
}

@Composable
private fun SectionLabel(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        style = MaterialTheme.typography.labelSmall,
        color = HabitTrackerTheme.colors.textTertiary,
        modifier = modifier.padding(bottom = 6.dp),
        fontWeight = FontWeight.SemiBold
    )
}

@Composable
private fun DeleteConfirmationDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Delete Habit") },
        text = { Text("Are you sure you want to delete this habit and all its progress? This cannot be undone.") },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text("Delete", color = HabitTrackerTheme.colors.destructive)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel", color = Color.White)
            }
        },
        containerColor = HabitTrackerTheme.colors.surface,
        titleContentColor = Color.White,
        textContentColor = HabitTrackerTheme.colors.textSecondary
    )
}

@Preview
@Composable
private fun EditorScreenPreview() {
    HabitTrackerTheme {
        EditorScreen(
            state = EditorState(name = "Morning Run"),
            onAction = {}
        )
    }
}
