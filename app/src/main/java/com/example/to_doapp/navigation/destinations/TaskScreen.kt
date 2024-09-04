package com.example.to_doapp.navigation.destinations

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.to_doapp.util.Action
import com.example.to_doapp.util.Constants

fun NavGraphBuilder.taskScreen(
    navigateToTaskListScreen: (Action) -> Unit
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
    ) {

    }
}