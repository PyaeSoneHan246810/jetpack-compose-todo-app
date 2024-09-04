package com.example.to_doapp.data.models

import androidx.compose.ui.graphics.Color
import com.example.to_doapp.ui.theme.HighPriorityColor
import com.example.to_doapp.ui.theme.LowPriorityColor
import com.example.to_doapp.ui.theme.MediumPriorityColor
import com.example.to_doapp.ui.theme.NonePriorityColor

enum class Priority(val color: Color) {
    NONE(color = NonePriorityColor),
    LOW(color = LowPriorityColor),
    MEDIUM(color = MediumPriorityColor),
    HIGH(color = HighPriorityColor)
}