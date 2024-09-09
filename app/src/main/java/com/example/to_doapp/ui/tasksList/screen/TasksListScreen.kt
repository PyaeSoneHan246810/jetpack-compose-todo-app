package com.example.to_doapp.ui.tasksList.screen

import android.content.res.Configuration
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
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

@Composable
fun TasksListScreen(
    modifier: Modifier = Modifier,
    allTasksResponse: Response<List<ToDoTask>>,
    searchTasksResponse: Response<List<ToDoTask>>,
    sortStateResponse: Response<Priority>,
    lowPriorityTasks: List<ToDoTask>,
    highPriorityTasks: List<ToDoTask>,
    snackBarHostState: SnackbarHostState,
    searchAppBarState: SearchAppBarState,
    searchQuery: String,
    onSearchActionClick: () -> Unit,
    onSearchQueryChange: (newQuery: String) -> Unit,
    onSearchAppBarClose: () -> Unit,
    onSearch: (searchQuery: String) -> Unit,
    onSortActionClick: (Priority) -> Unit,
    onDeleteAllActionClick: (action: Action) -> Unit,
    onSwipeToDelete: (action: Action, task: ToDoTask) -> Unit,
    navigateToTaskScreen: (taskId: Int) -> Unit,
) {
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
                onSearch = onSearch,
                onSortActionClick = onSortActionClick,
                onDeleteAllActionClick = onDeleteAllActionClick
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
            SnackbarHost(hostState = snackBarHostState) { snackBarData ->
                Snackbar(
                    snackbarData = snackBarData,
                    containerColor = MaterialTheme.colorScheme.surfaceContainer,
                    contentColor = MaterialTheme.colorScheme.onSurface
                )
            }

        }
    ) { paddingValues ->
        if (sortStateResponse is Response.Success) {
            val isSearchTriggered = searchAppBarState == SearchAppBarState.TRIGGERED
            when {
                isSearchTriggered -> {
                    if (searchTasksResponse is Response.Loading) {
                        LoadingTasks(
                            modifier = Modifier
                                .padding(paddingValues)
                        )
                    } else if (searchTasksResponse is Response.Success) {
                        TasksListContent(
                            modifier = Modifier
                                .padding(paddingValues),
                            tasks = searchTasksResponse.data,
                            onTaskItemClick = { taskId ->
                                navigateToTaskScreen(taskId)
                            },
                            onSwipeToDelete = { task ->
                                performOnSwipeToDeleteAction(onSwipeToDelete, task, snackBarHostState)
                            }
                        )
                    }
                }
                sortStateResponse.data == Priority.NONE -> {
                    if (allTasksResponse is Response.Loading) {
                        LoadingTasks(
                            modifier = Modifier
                                .padding(paddingValues)
                        )
                    } else if (allTasksResponse is Response.Success) {
                        TasksListContent(
                            modifier = Modifier
                                .padding(paddingValues),
                            tasks = allTasksResponse.data,
                            onTaskItemClick = { taskId ->
                                navigateToTaskScreen(taskId)
                            },
                            onSwipeToDelete = { task ->
                                performOnSwipeToDeleteAction(onSwipeToDelete, task, snackBarHostState)
                            }
                        )
                    }
                }
                sortStateResponse.data == Priority.LOW -> {
                    TasksListContent(
                        modifier = Modifier
                            .padding(paddingValues),
                        tasks = lowPriorityTasks,
                        onTaskItemClick = { taskId ->
                            navigateToTaskScreen(taskId)
                        },
                        onSwipeToDelete = { task ->
                            performOnSwipeToDeleteAction(onSwipeToDelete, task, snackBarHostState)
                        }
                    )
                }
                sortStateResponse.data == Priority.HIGH -> {
                    TasksListContent(
                        modifier = Modifier
                            .padding(paddingValues),
                        tasks = highPriorityTasks,
                        onTaskItemClick = { taskId ->
                            navigateToTaskScreen(taskId)
                        },
                        onSwipeToDelete = { task ->
                            performOnSwipeToDeleteAction(onSwipeToDelete, task, snackBarHostState)
                        }
                    )
                }
            }
        }
    }
}

private fun performOnSwipeToDeleteAction(onSwipeToDelete: (action: Action, task: ToDoTask) -> Unit, task: ToDoTask, snackBarHostState: SnackbarHostState) {
    onSwipeToDelete(Action.DELETE, task)
    snackBarHostState.currentSnackbarData?.dismiss()
}

@Composable
fun TasksListContent(
    modifier: Modifier = Modifier,
    tasks: List<ToDoTask>,
    onTaskItemClick: (taskId: Int) -> Unit,
    onSwipeToDelete: (task: ToDoTask) -> Unit
) {
    if (tasks.isEmpty()) {
        EmptyTasks(
            modifier = modifier
        )
    } else {
        TasksList(
            modifier = modifier,
            tasks = tasks,
            onTaskItemClick = onTaskItemClick,
            onSwipeToDelete = onSwipeToDelete
        )
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
            sortStateResponse = Response.Success(
                data = Priority.NONE
            ),
            lowPriorityTasks = listOf(),
            highPriorityTasks = listOf(),
            snackBarHostState = SnackbarHostState(),
            searchAppBarState = SearchAppBarState.CLOSED,
            searchQuery = "",
            onSearchActionClick = {},
            onSearchQueryChange = {},
            onSearchAppBarClose = {},
            onSearch = {},
            onSortActionClick = {},
            onDeleteAllActionClick = {},
            onSwipeToDelete = { _, _ ->  },
            navigateToTaskScreen = {},
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
            sortStateResponse = Response.Success(
                data = Priority.NONE
            ),
            lowPriorityTasks = listOf(),
            highPriorityTasks = listOf(),
            snackBarHostState = SnackbarHostState(),
            searchAppBarState = SearchAppBarState.CLOSED,
            searchQuery = "",
            onSearchActionClick = {},
            onSearchQueryChange = {},
            onSearchAppBarClose = {},
            onSearch = {},
            onSortActionClick = {},
            onDeleteAllActionClick = {},
            onSwipeToDelete = { _, _ ->  },
            navigateToTaskScreen = {},
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
            sortStateResponse = Response.Success(
                data = Priority.NONE
            ),
            lowPriorityTasks = listOf(),
            highPriorityTasks = listOf(),
            snackBarHostState = SnackbarHostState(),
            searchAppBarState = SearchAppBarState.CLOSED,
            searchQuery = "",
            onSearchActionClick = {},
            onSearchQueryChange = {},
            onSearchAppBarClose = {},
            onSearch = {},
            onSortActionClick = {},
            onDeleteAllActionClick = {},
            onSwipeToDelete = { _, _ ->  },
            navigateToTaskScreen = {},
        )
    }
}