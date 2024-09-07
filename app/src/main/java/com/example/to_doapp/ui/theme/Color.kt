package com.example.to_doapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

val NonePriorityColor = Color(0xFFCECECE)
val LowPriorityColor = Color(0xFF43BF57)
val MediumPriorityColor = Color(0xFFF8C22D)
val HighPriorityColor = Color(0xFFF2451C)

val SplashScreenColor: Color
    @Composable
    get() = if (isSystemInDarkTheme()) MaterialTheme.colorScheme.background else MaterialTheme.colorScheme.primary