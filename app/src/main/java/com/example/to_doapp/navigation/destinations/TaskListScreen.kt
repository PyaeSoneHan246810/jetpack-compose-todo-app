package com.example.to_doapp.navigation.destinations

import androidx.compose.material3.Text
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.to_doapp.util.Constants
fun NavGraphBuilder.taskListScreen(
    navigateToTaskScreen: (Int) -> Unit
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
    ) {
        Text(text = "Hello Jetpack Compose")
    }
}