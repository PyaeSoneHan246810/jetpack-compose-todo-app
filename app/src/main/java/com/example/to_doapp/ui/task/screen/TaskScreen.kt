package com.example.to_doapp.ui.task.screen

import android.content.res.Configuration
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.to_doapp.R
import com.example.to_doapp.components.PriorityDropDown
import com.example.to_doapp.data.model.Priority
import com.example.to_doapp.data.model.ToDoTask
import com.example.to_doapp.ui.task.component.TaskAppBar
import com.example.to_doapp.ui.theme.LARGE_PADDING
import com.example.to_doapp.ui.theme.Shapes
import com.example.to_doapp.ui.theme.ToDoAppTheme
import com.example.to_doapp.util.Action

@Composable
fun TaskScreen(
    modifier: Modifier = Modifier,
    selectedTask: ToDoTask?,
    taskTitle: String,
    taskDesc: String,
    taskPriority: Priority,
    onTitleChanged: (newTitle: String) -> Unit,
    onDescChanged: (newDesc: String) -> Unit,
    onPriorityChanged: (newPriority: Priority) -> Unit,
    navigateToTaskListScreen: (action: Action) -> Unit,
) {
    BackHandler(
        onBack = {
            navigateToTaskListScreen(Action.NO_ACTION)
        }
    )
    Scaffold(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TaskAppBar(
                selectedTask = selectedTask,
                navigateToTasksListScreen = navigateToTaskListScreen
            )
        }
    ) { paddingValues ->
        TaskContent(
            modifier = Modifier
                .padding(paddingValues),
            title = taskTitle,
            description = taskDesc,
            priority = taskPriority,
            onTitleChanged = onTitleChanged,
            onDescChanged = onDescChanged,
            onPriorityChanged = onPriorityChanged
        )
    }
}

@Composable
fun TaskContent(
    modifier: Modifier = Modifier,
    title: String,
    description: String,
    priority: Priority,
    onTitleChanged: (newTitle: String) -> Unit,
    onDescChanged: (newDesc: String) -> Unit,
    onPriorityChanged: (newPriority: Priority) -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(LARGE_PADDING),
        verticalArrangement = Arrangement.spacedBy(LARGE_PADDING),
    ) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = title,
            onValueChange = onTitleChanged,
            label = {
                Text(text = stringResource(id = R.string.title))
            },
            textStyle = MaterialTheme.typography.bodyLarge,
            colors = OutlinedTextFieldDefaults.colors(
                cursorColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant,
                focusedBorderColor = MaterialTheme.colorScheme.outlineVariant,
                unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                focusedTextColor = MaterialTheme.colorScheme.onSurface,
                unfocusedLabelColor = MaterialTheme.colorScheme.onSurface,
                focusedLabelColor = MaterialTheme.colorScheme.primary
            ),
            shape = Shapes.medium,
            singleLine = true
        )
        PriorityDropDown(
            priority = priority,
            onPrioritySelected = onPriorityChanged
        )
        OutlinedTextField(
            modifier = Modifier
                .fillMaxSize(),
            value = description,
            onValueChange = onDescChanged,
            label = {
                Text(text = stringResource(id = R.string.desc))
            },
            textStyle = MaterialTheme.typography.bodyLarge,
            colors = OutlinedTextFieldDefaults.colors(
                cursorColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant,
                focusedBorderColor = MaterialTheme.colorScheme.outlineVariant,
                unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                focusedTextColor = MaterialTheme.colorScheme.onSurface,
                unfocusedLabelColor = MaterialTheme.colorScheme.onSurface,
                focusedLabelColor = MaterialTheme.colorScheme.primary
            ),
            shape = Shapes.medium,
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, name = "Light Mode", showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark Mode", showBackground = true)
@Composable
private fun TaskScreenPrev() {
    ToDoAppTheme {
        TaskScreen(
            selectedTask = ToDoTask(
                id = 0,
                title = "This is the test title.",
                description = "This is the test description. This is the test description.",
                priority = Priority.HIGH
            ),
            taskTitle = "This is the test title.",
            taskDesc = "This is the test description. This is the test description.",
            taskPriority = Priority.HIGH,
            onTitleChanged = {},
            onDescChanged = {},
            onPriorityChanged = {},
            navigateToTaskListScreen = {},
        )
    }
}