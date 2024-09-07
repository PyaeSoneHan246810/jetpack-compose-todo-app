package com.example.to_doapp.navigation.destinations


import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.to_doapp.ui.splash.SplashScreen
import com.example.to_doapp.util.Constants

fun NavGraphBuilder.splashComposable(
    navigateToTaskListScreen: () -> Unit,
) {
    composable(
        route = Constants.SPLASH_ROUTE,
        exitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Down,
                animationSpec = tween(
                    durationMillis = 400,
                )
            )
        }
    ) {
        SplashScreen(
            navigateToTaskListScreen = navigateToTaskListScreen
        )
    }
}