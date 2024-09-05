package com.example.to_doapp.ui.task.screen

import android.content.res.Configuration
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.to_doapp.data.model.Priority
import com.example.to_doapp.data.model.ToDoTask
import com.example.to_doapp.ui.task.component.TaskAppBar
import com.example.to_doapp.ui.theme.ToDoAppTheme
import com.example.to_doapp.util.Action
import com.example.to_doapp.util.Response

@Composable
fun TaskScreen(
    modifier: Modifier = Modifier,
    taskResponse: Response<ToDoTask?>,
    navigateToTaskListScreen: (action: Action) -> Unit,
) {
    Scaffold(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TaskAppBar(
                taskResponse = taskResponse,
                navigateToTasksListScreen = navigateToTaskListScreen
            )
        }
    ) { paddingValues ->
        TaskContent(
            modifier = Modifier
                .padding(paddingValues)
        )
    }
}

@Composable
fun TaskContent(
    modifier: Modifier = Modifier,
) {
    
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, name = "Light Mode", showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark Mode", showBackground = true)
@Composable
private fun TaskScreenPrev() {
    ToDoAppTheme {
        TaskScreen(
            taskResponse = Response.Success(
                data = ToDoTask(
                    id = 0,
                    title = "This is the test title.",
                    description = "This is the test description. This is the test description.",
                    priority = Priority.HIGH
                ),
            ),
            navigateToTaskListScreen = {}
        )
    }
}