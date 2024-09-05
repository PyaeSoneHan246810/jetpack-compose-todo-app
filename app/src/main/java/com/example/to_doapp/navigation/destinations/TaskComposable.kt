package com.example.to_doapp.navigation.destinations

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.to_doapp.ui.task.screen.TaskScreen
import com.example.to_doapp.ui.viewmodel.SharedViewModel
import com.example.to_doapp.util.Action
import com.example.to_doapp.util.Constants

fun NavGraphBuilder.taskComposable(
    navigateToTaskListScreen: (action: Action) -> Unit,
    sharedViewModel: SharedViewModel
) {
    composable(
        route = Constants.TASK_ROUTE,
        arguments = listOf(
            navArgument(
                name = Constants.TASK_ROUTE_ARG1
            ) {
                type = NavType.IntType
            }
        )
    ) { navBackStackEntry ->
        val taskId = navBackStackEntry.arguments?.getInt(Constants.TASK_ROUTE_ARG1) ?: -1
        LaunchedEffect(key1 = true) {
            sharedViewModel.getSelectedTask(taskId = taskId)
        }
        val taskResponse by sharedViewModel.taskResponse.collectAsState()
        TaskScreen(
            taskResponse = taskResponse,
            navigateToTaskListScreen = navigateToTaskListScreen,
        )
    }
}