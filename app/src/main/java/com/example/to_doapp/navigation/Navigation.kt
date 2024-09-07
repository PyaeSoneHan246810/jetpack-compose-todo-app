package com.example.to_doapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.to_doapp.navigation.destinations.splashComposable
import com.example.to_doapp.navigation.destinations.taskComposable
import com.example.to_doapp.navigation.destinations.tasksListComposable
import com.example.to_doapp.ui.viewmodel.SharedViewModel
import com.example.to_doapp.util.Constants

@Composable
fun Navigation(
    navController: NavHostController,
    sharedViewModel: SharedViewModel,
) {
    val navigationAction = remember(navController) {
        NavigationAction(navController = navController)
    }
    NavHost(
        navController = navController,
        startDestination = Constants.SPLASH_ROUTE
    ) {
        splashComposable(
            navigateToTaskListScreen = navigationAction.splashScreenToTasksListScreen
        )
        tasksListComposable(
            navigateToTaskScreen = navigationAction.tasksListScreenToTaskScreen,
            sharedViewModel = sharedViewModel,
        )
        taskComposable(
            navigateToTaskListScreen = navigationAction.taskScreenToTasksListScreen,
            sharedViewModel = sharedViewModel
        )
    }
}