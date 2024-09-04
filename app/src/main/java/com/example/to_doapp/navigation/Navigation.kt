package com.example.to_doapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.to_doapp.navigation.destinations.taskListScreen
import com.example.to_doapp.navigation.destinations.taskScreen
import com.example.to_doapp.util.Constants
@Composable
fun Navigation(
    navController: NavHostController
) {
    val screens = remember(navController) {
        Screens(navController = navController)
    }
    NavHost(
        navController = navController,
        startDestination = Constants.TASKS_LIST_ROUTE
    ) {
        taskListScreen(
            navigateToTaskScreen = screens.task
        )
        taskScreen(
            navigateToTaskListScreen = screens.tasksList
        )
    }
}