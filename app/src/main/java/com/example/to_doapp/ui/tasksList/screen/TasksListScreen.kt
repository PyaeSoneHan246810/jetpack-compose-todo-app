package com.example.to_doapp.ui.tasksList.screen

import android.content.res.Configuration
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.to_doapp.data.model.Priority
import com.example.to_doapp.data.model.ToDoTask
import com.example.to_doapp.ui.tasksList.component.AddItemFab
import com.example.to_doapp.ui.tasksList.component.EmptyTasks
import com.example.to_doapp.ui.tasksList.component.LoadingTasks
import com.example.to_doapp.ui.tasksList.component.MainAppbar
import com.example.to_doapp.ui.tasksList.component.TasksList
import com.example.to_doapp.ui.tasksList.state.SearchAppBarState
import com.example.to_doapp.ui.theme.ToDoAppTheme
import com.example.to_doapp.util.Response

@Composable
fun TasksListScreen(
    modifier: Modifier = Modifier,
    tasksResponse: Response<List<ToDoTask>>,
    onTaskItemClick: (taskId: Int) -> Unit,
    navigateToTaskScreen: (taskId: Int) -> Unit,
    searchAppBarState: SearchAppBarState,
    searchQuery: String,
    onSearchActionClick: () -> Unit,
    onSearchQueryChange: (newQuery: String) -> Unit,
    onSearchAppBarClose: () -> Unit,
) {
    Scaffold(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            MainAppbar(
                searchAppBarState = searchAppBarState,
                searchQuery = searchQuery,
                onSearchActionClick = onSearchActionClick,
                onSearchQueryChange = onSearchQueryChange,
                onSearchAppBarClose = onSearchAppBarClose,
            )
        },
        floatingActionButton = {
            AddItemFab(
                onClick = {
                    navigateToTaskScreen(-1)
                }
            )
        }
    ) { paddingValues ->
        TasksListContent(
            modifier = Modifier
                .padding(paddingValues),
            tasksResponse = tasksResponse,
            onTaskItemClick = onTaskItemClick
        )
    }
}

@Composable
fun TasksListContent(
    modifier: Modifier = Modifier,
    tasksResponse: Response<List<ToDoTask>>,
    onTaskItemClick: (taskId: Int) -> Unit,
) {
    if (tasksResponse is Response.Loading) {
        LoadingTasks(
            modifier = modifier
        )
    } else if (tasksResponse is Response.Success) {
        if (tasksResponse.data.isEmpty()) {
            EmptyTasks(
                modifier = modifier
            )
        } else {
            TasksList(
                modifier = modifier,
                tasks = tasksResponse.data,
                onTaskItemClick = onTaskItemClick
            )
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, name = "Light Mode", showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark Mode", showBackground = true)
@Composable
private fun TasksListScreenPrev() {
    ToDoAppTheme {
        TasksListScreen(
            tasksResponse = Response.Success(
                data = listOf(
                    ToDoTask(
                        id = 0,
                        title = "This is the test title.",
                        description = "This is the test description. This is the test description.",
                        priority = Priority.HIGH
                    ),
                    ToDoTask(
                        id = 1,
                        title = "This is the test title.",
                        description = "This is the test description. This is the test description.",
                        priority = Priority.MEDIUM
                    ),
                    ToDoTask(
                        id = 2,
                        title = "This is the test title.",
                        description = "This is the test description. This is the test description.",
                        priority = Priority.LOW
                    )
                )
            ),
            onTaskItemClick = {},
            navigateToTaskScreen = {},
            searchAppBarState = SearchAppBarState.CLOSED,
            searchQuery = "",
            onSearchActionClick = {},
            onSearchQueryChange = {},
            onSearchAppBarClose = {},
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, name = "Empty Light Mode", showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Empty Dark Mode", showBackground = true)
@Composable
private fun EmptyTasksListScreenPrev() {
    ToDoAppTheme {
        TasksListScreen(
            tasksResponse = Response.Success(
                data = listOf()
            ),
            onTaskItemClick = {},
            navigateToTaskScreen = {},
            searchAppBarState = SearchAppBarState.CLOSED,
            searchQuery = "",
            onSearchActionClick = {},
            onSearchQueryChange = {},
            onSearchAppBarClose = {},
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, name = "Loading Light Mode", showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Loading Dark Mode", showBackground = true)
@Composable
private fun LoadingTasksListScreenPrev() {
    ToDoAppTheme {
        TasksListScreen(
            tasksResponse = Response.Loading,
            onTaskItemClick = {},
            navigateToTaskScreen = {},
            searchAppBarState = SearchAppBarState.CLOSED,
            searchQuery = "",
            onSearchActionClick = {},
            onSearchQueryChange = {},
            onSearchAppBarClose = {},
        )
    }
}