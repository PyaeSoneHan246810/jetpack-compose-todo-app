package com.example.to_doapp.navigation.destinations

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
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
        var actionSaved by rememberSaveable {
            mutableStateOf(Action.NO_ACTION)
        }
        var actionState by rememberSaveable {
            mutableStateOf(navBackStackEntry.arguments!!.getString(Constants.TASKS_LIST_ROUTE_ARG1).toAction())
        }
        val allTasksResponse by sharedViewModel.allTasksResponse.collectAsState()
        val searchTasksResponse by sharedViewModel.searchTasksResponse.collectAsState()
        val sortStateResponse by sharedViewModel.sortStateResponse.collectAsState()
        val lowPriorityTasks by sharedViewModel.lowPriorityTasks.collectAsState()
        val highPriorityTasks by sharedViewModel.highPriorityTasks.collectAsState()
        LaunchedEffect(key1 = actionState) {
            if (actionState != actionSaved) {
                actionSaved = actionState
                sharedViewModel.action.value = actionState
            }
        }
        val databaseAction by sharedViewModel.action
        LaunchedEffect(key1 = databaseAction) {
            sharedViewModel.handleDatabaseAction(databaseAction)
        }
        TasksListScreen(
            allTasksResponse = allTasksResponse,
            searchTasksResponse = searchTasksResponse,
            sortStateResponse = sortStateResponse,
            lowPriorityTasks = lowPriorityTasks,
            highPriorityTasks = highPriorityTasks,
            action = actionState,
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
                actionState = action
            },
            onUndoClick = { action ->
                sharedViewModel.action.value = action
                actionState = action
            },
            navigateToTaskScreen = navigateToTaskScreen,
            onSwipeToDelete = { action, task ->
                sharedViewModel.action.value = action
                actionState = action
                sharedViewModel.updateTaskProperties(task)
            },
            resetDatabaseAction = {
                sharedViewModel.resetDatabaseAction()
            }
        )
    }
}