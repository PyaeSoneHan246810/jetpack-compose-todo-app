package com.example.to_doapp.navigation.destinations

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.to_doapp.ui.tasksList.screen.TasksListScreen
import com.example.to_doapp.ui.tasksList.state.SearchAppBarState
import com.example.to_doapp.ui.viewmodel.SharedViewModel
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
        val action = navBackStackEntry.arguments!!.getString(Constants.TASKS_LIST_ROUTE_ARG1).toAction()
        LaunchedEffect(key1 = true) {
            sharedViewModel.getAllTasks()
        }
        val allTasksResponse by sharedViewModel.allTasksResponse.collectAsState()
        LaunchedEffect(key1 = action) {
            sharedViewModel.action.value = action
        }
        val databaseAction by sharedViewModel.action
        LaunchedEffect(key1 = databaseAction) {
            sharedViewModel.handleDatabaseAction(databaseAction)
        }
        TasksListScreen(
            tasksResponse = allTasksResponse,
            action = action,
            navigateToTaskScreen = navigateToTaskScreen,
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
            }
        )
    }
}