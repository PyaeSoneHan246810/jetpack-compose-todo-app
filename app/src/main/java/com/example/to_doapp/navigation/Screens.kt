package com.example.to_doapp.navigation

import androidx.navigation.NavHostController
import com.example.to_doapp.util.Action
import com.example.to_doapp.util.Constants

class Screens(navController: NavHostController) {
    val tasksList: (action: Action) -> Unit  = { action ->
        navController.navigate("task_list/${action.name}") {
            popUpTo(Constants.TASKS_LIST_ROUTE) {
                inclusive = true
            }
        }
    }
    val task: (taskId: Int) -> Unit = { taskId ->
        navController.navigate("task/$taskId")
    }
}