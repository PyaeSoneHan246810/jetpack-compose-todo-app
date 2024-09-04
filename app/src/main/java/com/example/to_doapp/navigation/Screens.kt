package com.example.to_doapp.navigation

import androidx.navigation.NavHostController
import com.example.to_doapp.util.Action
import com.example.to_doapp.util.Constants

class Screens(navController: NavHostController) {
    val tasksList: (Action) -> Unit  = { action ->
        navController.navigate("task_list/${action.name}") {
            popUpTo(Constants.TASKS_LIST_ROUTE) {
                inclusive = true
            }
        }
    }
    val task: (Int) -> Unit = { id ->
        navController.navigate("task/$id")
    }
}