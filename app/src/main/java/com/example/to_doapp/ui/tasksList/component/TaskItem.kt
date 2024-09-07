package com.example.to_doapp.ui.tasksList.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.example.to_doapp.R
import com.example.to_doapp.data.model.Priority
import com.example.to_doapp.data.model.ToDoTask
import com.example.to_doapp.ui.theme.EXTRA_LARGE_PADDING
import com.example.to_doapp.ui.theme.HighPriorityColor
import com.example.to_doapp.ui.theme.LARGE_PADDING
import com.example.to_doapp.ui.theme.MEDIUM_PADDING
import com.example.to_doapp.ui.theme.PRIORITY_INDICATOR_SIZE
import com.example.to_doapp.ui.theme.Shapes
import com.example.to_doapp.ui.theme.ToDoAppTheme

@Composable
fun TaskItem(
    modifier: Modifier = Modifier,
    toDoTask: ToDoTask,
    onTaskItemClick: (taskId: Int) -> Unit
) {
    Surface(
        modifier = modifier
            .fillMaxWidth(),
        color = MaterialTheme.colorScheme.surface,
        shape = Shapes.small,
        onClick = {
            onTaskItemClick(toDoTask.id)
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(LARGE_PADDING)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                Text(
                    modifier = Modifier
                        .weight(9f),
                    text = toDoTask.title,
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Box(
                    modifier = Modifier
                        .weight(1f),
                    contentAlignment = Alignment.TopEnd
                ) {
                    Canvas(
                        modifier = Modifier.size(PRIORITY_INDICATOR_SIZE))
                    {
                        drawCircle(
                            color = toDoTask.priority.color
                        )
                    }
                }
            }
            Spacer(
                modifier = Modifier.height(MEDIUM_PADDING)
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = toDoTask.description,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun SwipeToDismissTaskBackground(
    modifier: Modifier = Modifier,
    degree: Float,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .clip(Shapes.small)
            .background(
                color = HighPriorityColor
            )
            .padding(horizontal = EXTRA_LARGE_PADDING),
        contentAlignment = Alignment.CenterEnd
    ) {
        Icon(
            modifier = Modifier
                .rotate(degree),
            imageVector = Icons.Filled.Delete,
            contentDescription = stringResource(id = R.string.delete_task),
            tint = Color.White
        )
    }
}

@Preview
@Composable
private fun TaskItemPrev() {
    ToDoAppTheme {
        TaskItem(
            toDoTask = ToDoTask(
                id = 0,
                title = "This is the test title.",
                description = "This is the test description. This is the test description.",
                priority = Priority.HIGH
            ),
            onTaskItemClick = {}
        )
    }
}

@Preview
@Composable
private fun DeleteBackgroundPrev() {
    ToDoAppTheme {
        SwipeToDismissTaskBackground(
            degree = 0f
        )
    }
}