package com.example.to_doapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.to_doapp.navigation.destinations.taskComposable
import com.example.to_doapp.navigation.destinations.tasksListComposable
import com.example.to_doapp.ui.viewmodel.SharedViewModel
import com.example.to_doapp.util.Constants

@Composable
fun Navigation(
    navController: NavHostController,
    sharedViewModel: SharedViewModel,
) {
    val screens = remember(navController) {
        Screens(navController = navController)
    }
    NavHost(
        navController = navController,
        startDestination = Constants.TASKS_LIST_ROUTE
    ) {
        tasksListComposable(
            navigateToTaskScreen = screens.task,
            sharedViewModel = sharedViewModel,
        )
        taskComposable(
            navigateToTaskListScreen = screens.tasksList,
            sharedViewModel = sharedViewModel
        )
    }
}