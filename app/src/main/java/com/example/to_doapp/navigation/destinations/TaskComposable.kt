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
        val taskId = navBackStackEntry.arguments!!.getInt(Constants.TASK_ROUTE_ARG1)
        LaunchedEffect(key1 = taskId) {
            sharedViewModel.getSelectedTask(taskId = taskId)
        }
        val selectedTask by sharedViewModel.selectedTask.collectAsState()
        LaunchedEffect(key1 = selectedTask) {
            sharedViewModel.updateTaskProperties(selectedTask)
        }
        TaskScreen(
            selectedTask = selectedTask,
            taskTitle = sharedViewModel.taskTitle.value,
            taskDesc = sharedViewModel.taskDesc.value,
            taskPriority = sharedViewModel.taskPriority.value,
            navigateToTaskListScreen = navigateToTaskListScreen,
            onTitleChanged = { newTitle ->
                sharedViewModel.updateTitle(newTitle)
            },
            onDescChanged = { newDesc ->
                sharedViewModel.taskDesc.value = newDesc
            },
            onPriorityChanged = { newPriority ->
                sharedViewModel.taskPriority.value = newPriority
            }
        )
    }
}