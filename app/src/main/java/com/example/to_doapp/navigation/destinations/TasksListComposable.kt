package com.example.to_doapp.navigation.destinations

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.to_doapp.ui.tasksList.screen.TasksListScreen
import com.example.to_doapp.ui.tasksList.state.SearchAppBarState
import com.example.to_doapp.ui.viewmodel.SharedViewModel
import com.example.to_doapp.util.Action
import com.example.to_doapp.util.Constants
import com.example.to_doapp.util.toAction
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun NavGraphBuilder.tasksListComposable(
    navigateToTaskScreen: (taskId: Int) -> Unit,
    sharedViewModel: SharedViewModel,
) {
    composable(
        route = Constants.TASKS_LIST_ROUTE,
        arguments = listOf(
            navArgument(
                name = Constants.TASKS_LIST_ROUTE_ARG1
            ){
                type = NavType.StringType
            },
        )
    ) { navBackStackEntry ->
        val allTasksResponse by sharedViewModel.allTasksResponse.collectAsState()
        val searchTasksResponse by sharedViewModel.searchTasksResponse.collectAsState()
        val sortStateResponse by sharedViewModel.sortStateResponse.collectAsState()
        val lowPriorityTasks by sharedViewModel.lowPriorityTasks.collectAsState()
        val highPriorityTasks by sharedViewModel.highPriorityTasks.collectAsState()
        var userActionSaved by rememberSaveable {
            mutableStateOf(Action.NO_ACTION)
        }
        val userAction = navBackStackEntry.arguments!!.getString(Constants.TASKS_LIST_ROUTE_ARG1).toAction()
        LaunchedEffect(key1 = userAction) {
            if (userAction != userActionSaved) {
                sharedViewModel.action.value = userAction
            }
        }
        val databaseAction by sharedViewModel.action
        LaunchedEffect(key1 = databaseAction) {
            sharedViewModel.handleDatabaseAction(databaseAction)
        }
        val coroutineScope = rememberCoroutineScope()
        val snackBarHostState = remember {
            SnackbarHostState()
        }
        var showDeleteSuccessSnackBar by remember {
            mutableStateOf(false)
        }
        var showDeleteAllSuccessSnackBar by remember {
            mutableStateOf(false)
        }
        LaunchedEffect(key1 = userAction) {
            if (userAction != userActionSaved) {
                userActionSaved = userAction
                val message: String? = when(userAction) {
                    Action.ADD -> "Task was added successfully."
                    Action.UPDATE -> "Task was updated successfully."
                    else -> null
                }
                when(userAction) {
                    Action.ADD, Action.UPDATE, Action.DELETE_ALL -> {
                        displaySnackBar(
                            coroutineScope = coroutineScope,
                            snackBarHostState = snackBarHostState,
                            message = message ?: "",
                            onDismissed = {
                                sharedViewModel.action.value = Action.NO_ACTION
                            }
                        )
                    }
                    else -> {}
                }
            }
        }
        LaunchedEffect(key1 = showDeleteSuccessSnackBar) {
            if (showDeleteSuccessSnackBar) {
                displayUndoSnackBar(
                    coroutineScope = coroutineScope,
                    snackBarHostState = snackBarHostState,
                    message = "Successfully deleted the task.",
                    onUndoClick = {
                        sharedViewModel.action.value = Action.UNDO
                    },
                    onDismissed = {
                        sharedViewModel.action.value = Action.NO_ACTION
                    }
                )
                showDeleteSuccessSnackBar = false
            }
        }
        LaunchedEffect(key1 = showDeleteAllSuccessSnackBar) {
            if (showDeleteAllSuccessSnackBar) {
                displaySnackBar(
                    coroutineScope = coroutineScope,
                    snackBarHostState = snackBarHostState,
                    message = "All tasks are deleted successfully.",
                    onDismissed = {
                        sharedViewModel.action.value = Action.NO_ACTION
                    }
                )
                showDeleteAllSuccessSnackBar = false
            }
        }
        TasksListScreen(
            allTasksResponse = allTasksResponse,
            searchTasksResponse = searchTasksResponse,
            sortStateResponse = sortStateResponse,
            lowPriorityTasks = lowPriorityTasks,
            highPriorityTasks = highPriorityTasks,
            snackBarHostState = snackBarHostState,
            searchAppBarState = sharedViewModel.searchAppBarState.value,
            searchQuery = sharedViewModel.searchQueryState.value,
            onSearchActionClick = {
                sharedViewModel.searchAppBarState.value = SearchAppBarState.OPENED
            },
            onSearchQueryChange = { newQuery ->
                sharedViewModel.searchQueryState.value = newQuery
            },
            onSearchAppBarClose = {
                if (sharedViewModel.searchQueryState.value.isNotEmpty()) {
                    sharedViewModel.searchQueryState.value = ""
                } else {
                    sharedViewModel.searchAppBarState.value = SearchAppBarState.CLOSED
                }
            },
            onSearch = { searchQuery ->
                sharedViewModel.getSearchTasks(searchQuery)
            },
            onSortActionClick = { priority ->
                sharedViewModel.saveSortState(priority)
            },
            onDeleteAllActionClick = { action ->
                sharedViewModel.action.value = action
                showDeleteAllSuccessSnackBar = true
            },
            onSwipeToDelete = { action, task ->
                sharedViewModel.action.value = action
                sharedViewModel.updateTaskProperties(task)
                showDeleteSuccessSnackBar = true
            },
            navigateToTaskScreen = navigateToTaskScreen,
        )
    }
}

fun displaySnackBar(coroutineScope: CoroutineScope, snackBarHostState: SnackbarHostState, message: String, onDismissed: () -> Unit) {
    coroutineScope.launch {
        val result = snackBarHostState.showSnackbar(
            message = message,
            actionLabel = "Ok",
            duration = SnackbarDuration.Short,
        )
        when(result) {
            SnackbarResult.ActionPerformed -> {

            }
            SnackbarResult.Dismissed -> {
                onDismissed()
            }
        }
    }
}

fun displayUndoSnackBar(coroutineScope: CoroutineScope, snackBarHostState: SnackbarHostState, message: String, onUndoClick: () -> Unit, onDismissed: () -> Unit) {
    coroutineScope.launch {
        val result = snackBarHostState.showSnackbar(
            message = message,
            actionLabel = "Undo",
            duration = SnackbarDuration.Long
        )
        when(result) {
            SnackbarResult.ActionPerformed -> {
                onUndoClick()
            }
            SnackbarResult.Dismissed -> {
                onDismissed()
            }
        }
    }
}