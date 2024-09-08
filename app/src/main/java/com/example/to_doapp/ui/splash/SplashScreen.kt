package com.example.to_doapp.ui.splash

import android.content.res.Configuration
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.to_doapp.R
import com.example.to_doapp.ui.theme.SPLAsH_LOGO_SIZE
import com.example.to_doapp.ui.theme.SplashScreenColor
import com.example.to_doapp.ui.theme.ToDoAppTheme
import com.example.to_doapp.util.Constants
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    navigateToTaskListScreen: () -> Unit,
) {
    var startAnimation by remember {
        mutableStateOf(false)
    }
    val offsetState by animateDpAsState(
        targetValue = if (startAnimation) 0.dp else 100.dp,
        animationSpec = tween(
            durationMillis = 1000
        ),
        label = "Offset State"
    )
    val alphaState by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(
            durationMillis = 1000
        ),
        label = "Alpha State"
    )
    LaunchedEffect(key1 = true) {
        startAnimation = true
        delay(Constants.SPLASH_SCREEN_DELAY)
        navigateToTaskListScreen()
    }
    SplashScreenContent(
        modifier = modifier,
        offsetState = offsetState,
        alphaState = alphaState
    )
}

@Composable
fun SplashScreenContent(
    modifier: Modifier = Modifier,
    offsetState: Dp,
    alphaState: Float
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                color = SplashScreenColor
            ),
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier
                .size(SPLAsH_LOGO_SIZE)
                .offset(
                    y = offsetState
                )
                .alpha(alphaState),
            painter = painterResource(id = R.drawable.splash_logo),
            contentDescription = stringResource(id = R.string.splash_logo),
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, name = "Light Mode", showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark Mode", showBackground = true)
@Composable
private fun SplashScreenPrev() {
    ToDoAppTheme {
        SplashScreenContent(
            offsetState = 0.dp,
            alphaState = 1f
        )
    }
}