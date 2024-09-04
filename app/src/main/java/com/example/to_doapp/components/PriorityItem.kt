package com.example.to_doapp.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.to_doapp.data.model.Priority
import com.example.to_doapp.ui.theme.LARGE_PADDING
import com.example.to_doapp.ui.theme.PRIORITY_INDICATOR_SIZE
import com.example.to_doapp.ui.theme.ToDoAppTheme

@Composable
fun PriorityItem(
    modifier: Modifier = Modifier,
    priority: Priority
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Canvas(modifier = Modifier.size(PRIORITY_INDICATOR_SIZE)) {
            drawCircle(
                color = priority.color
            )
        }
        Spacer(modifier = Modifier.width(LARGE_PADDING))
        Text(
            text = priority.title,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Preview
@Composable
private fun PriorityItemPrev() {
    ToDoAppTheme {
        PriorityItem(priority = Priority.LOW)
    }
}