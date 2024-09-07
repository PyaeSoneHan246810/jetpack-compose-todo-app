package com.example.to_doapp.navigation

import androidx.navigation.NavHostController
import com.example.to_doapp.util.Action
import com.example.to_doapp.util.Constants

class NavigationAction(navController: NavHostController) {
    val splashScreenToTasksListScreen: () -> Unit = {
        navController.navigate("task_list/${Action.NO_ACTION.name}") {
            popUpTo(Constants.SPLASH_ROUTE) {
                inclusive = true
            }
        }
    }
    val taskScreenToTasksListScreen: (action: Action) -> Unit  = { action ->
        navController.navigate("task_list/${action.name}") {
            popUpTo(Constants.TASKS_LIST_ROUTE) {
                inclusive = true
            }
        }
    }
    val tasksListScreenToTaskScreen: (taskId: Int) -> Unit = { taskId ->
        navController.navigate("task/$taskId")
    }
}