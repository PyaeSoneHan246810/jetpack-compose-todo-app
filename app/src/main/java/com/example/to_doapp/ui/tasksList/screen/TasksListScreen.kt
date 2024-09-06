package com.example.to_doapp.ui.tasksList.screen

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.to_doapp.data.model.Priority
import com.example.to_doapp.data.model.ToDoTask
import com.example.to_doapp.ui.tasksList.component.AddItemFab
import com.example.to_doapp.ui.tasksList.component.EmptyTasks
import com.example.to_doapp.ui.tasksList.component.LoadingTasks
import com.example.to_doapp.ui.tasksList.component.TasksList
import com.example.to_doapp.ui.tasksList.component.TasksListAppBar
import com.example.to_doapp.ui.tasksList.state.SearchAppBarState
import com.example.to_doapp.ui.theme.ToDoAppTheme
import com.example.to_doapp.util.Action
import com.example.to_doapp.util.Response
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun TasksListScreen(
    modifier: Modifier = Modifier,
    allTasksResponse: Response<List<ToDoTask>>,
    searchTasksResponse: Response<List<ToDoTask>>,
    action: Action,
    navigateToTaskScreen: (taskId: Int) -> Unit,
    searchAppBarState: SearchAppBarState,
    searchQuery: String,
    onSearchActionClick: () -> Unit,
    onSearchQueryChange: (newQuery: String) -> Unit,
    onSearchAppBarClose: () -> Unit,
    onSearch: (searchQuery: String) -> Unit,
    onUndoClick: (Action) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val snackBarHostState = remember {
        SnackbarHostState()
    }
    LaunchedEffect(key1 = action) {
        if (action == Action.ADD) {
            displaySnackBar(
                coroutineScope = coroutineScope,
                snackBarHostState = snackBarHostState,
                message = "Task was added successfully."
            )
        }
        if (action == Action.UPDATE) {
            displaySnackBar(
                coroutineScope = coroutineScope,
                snackBarHostState = snackBarHostState,
                message = "Task was updated successfully."
            )
        }
        if (action == Action.DELETE) {
            displayUndoSnackBar(
                coroutineScope = coroutineScope,
                snackBarHostState = snackBarHostState,
                onUndoClick = {
                    onUndoClick(Action.UNDO)
                }
            )
        }
    }
    Scaffold(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TasksListAppBar(
                searchAppBarState = searchAppBarState,
                searchQuery = searchQuery,
                onSearchActionClick = onSearchActionClick,
                onSearchQueryChange = onSearchQueryChange,
                onSearchAppBarClose = onSearchAppBarClose,
                onSearch = onSearch
            )
        },
        floatingActionButton = {
            AddItemFab(
                onClick = {
                    navigateToTaskScreen(-1)
                }
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        }
    ) { paddingValues ->
        val isSearchTriggered = searchAppBarState == SearchAppBarState.TRIGGERED
        val response = if (isSearchTriggered) searchTasksResponse else allTasksResponse
        if (response is Response.Loading) {
            LoadingTasks(
                modifier = Modifier
                    .padding(paddingValues)
            )
        } else if (response is Response.Success) {

            TasksListContent(
                modifier = Modifier
                    .padding(paddingValues),
                tasks = response.data,
                onTaskItemClick = { taskId ->
                    navigateToTaskScreen(taskId)
                }
            )
        }
    }
}

@Composable
fun TasksListContent(
    modifier: Modifier = Modifier,
    tasks: List<ToDoTask>,
    onTaskItemClick: (taskId: Int) -> Unit,
) {
    if (tasks.isEmpty()) {
        EmptyTasks(
            modifier = modifier
        )
    } else {
        TasksList(
            modifier = modifier,
            tasks = tasks,
            onTaskItemClick = onTaskItemClick
        )
    }
}

fun displaySnackBar(coroutineScope: CoroutineScope, snackBarHostState: SnackbarHostState, message: String) {
    coroutineScope.launch {
        snackBarHostState.showSnackbar(
            message = message,
            actionLabel = "Ok",
            duration = SnackbarDuration.Short,
        )
    }
}

fun displayUndoSnackBar(coroutineScope: CoroutineScope, snackBarHostState: SnackbarHostState, onUndoClick: () -> Unit) {
    coroutineScope.launch {
        val result = snackBarHostState.showSnackbar(
            message = "Successfully deleted the task.",
            actionLabel = "Undo",
            duration = SnackbarDuration.Long
        )
        when(result) {
            SnackbarResult.ActionPerformed -> {
                onUndoClick()
            }
            SnackbarResult.Dismissed -> {

            }
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, name = "Light Mode", showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark Mode", showBackground = true)
@Composable
private fun TasksListScreenPrev() {
    ToDoAppTheme {
        TasksListScreen(
            allTasksResponse = Response.Success(
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
            searchTasksResponse = Response.Success(
                data = listOf(
                    ToDoTask(
                        id = 0,
                        title = "This is the test title.",
                        description = "This is the test description. This is the test description.",
                        priority = Priority.HIGH
                    ),
                )
            ),
            action = Action.NO_ACTION,
            navigateToTaskScreen = {},
            searchAppBarState = SearchAppBarState.CLOSED,
            searchQuery = "",
            onSearchActionClick = {},
            onSearchQueryChange = {},
            onSearchAppBarClose = {},
            onSearch = {},
            onUndoClick = {}
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, name = "Empty Light Mode", showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Empty Dark Mode", showBackground = true)
@Composable
private fun EmptyTasksListScreenPrev() {
    ToDoAppTheme {
        TasksListScreen(
            allTasksResponse = Response.Success(
                data = listOf()
            ),
            searchTasksResponse = Response.Success(
                data = listOf()
            ),
            action = Action.NO_ACTION,
            navigateToTaskScreen = {},
            searchAppBarState = SearchAppBarState.CLOSED,
            searchQuery = "",
            onSearchActionClick = {},
            onSearchQueryChange = {},
            onSearchAppBarClose = {},
            onSearch = {},
            onUndoClick = {}
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, name = "Loading Light Mode", showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Loading Dark Mode", showBackground = true)
@Composable
private fun LoadingTasksListScreenPrev() {
    ToDoAppTheme {
        TasksListScreen(
            allTasksResponse = Response.Loading,
            searchTasksResponse = Response.Loading,
            action = Action.NO_ACTION,
            navigateToTaskScreen = {},
            searchAppBarState = SearchAppBarState.CLOSED,
            searchQuery = "",
            onSearchActionClick = {},
            onSearchQueryChange = {},
            onSearchAppBarClose = {},
            onSearch = {},
            onUndoClick = {}
        )
    }
}