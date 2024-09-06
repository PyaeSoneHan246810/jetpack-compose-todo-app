package com.example.to_doapp.data.model

import androidx.compose.ui.graphics.Color
import com.example.to_doapp.ui.theme.HighPriorityColor
import com.example.to_doapp.ui.theme.LowPriorityColor
import com.example.to_doapp.ui.theme.MediumPriorityColor
import com.example.to_doapp.ui.theme.NonePriorityColor

enum class Priority(val color: Color, val title: String) {
    NONE(color = NonePriorityColor, title = "None"),
    LOW(color = LowPriorityColor, title = "Low"),
    MEDIUM(color = MediumPriorityColor, title = "Medium"),
    HIGH(color = HighPriorityColor, title = "High")
}

fun String.toPriority(): Priority {
    return when(this) {
        "None" -> Priority.NONE
        "Low" -> Priority.LOW
        "Medium" -> Priority.MEDIUM
        "High" -> Priority.HIGH
        else -> Priority.NONE
    }
}