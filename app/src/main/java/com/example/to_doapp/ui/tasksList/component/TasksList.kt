package com.example.to_doapp.ui.tasksList.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.to_doapp.data.model.Priority
import com.example.to_doapp.data.model.ToDoTask
import com.example.to_doapp.ui.theme.MEDIUM_PADDING
import com.example.to_doapp.ui.theme.ToDoAppTheme

@Composable
fun TasksList(
    modifier: Modifier = Modifier,
    tasks: List<ToDoTask>,
    onTaskItemClick: (taskId: Int) -> Unit
) {
    LazyColumn(
        modifier = modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(MEDIUM_PADDING),
    ) {
        items(
            items = tasks,
            key = { task ->
                task.id
            }
        ) { task ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                TaskItem(
                    toDoTask = task,
                    onTaskItemClick = onTaskItemClick
                )
            }
        }
    }
}

@Preview
@Composable
private fun TasksListPrev() {
    ToDoAppTheme {
        TasksList(
            tasks = listOf(
                ToDoTask(
                    id = 0,
                    title = "This is the test title.",
                    description = "This is the test description. This is the test description.",
                    priority = Priority.HIGH
                ),
                ToDoTask(
                    id = 1,
                    title = "This is the test title.",
                    description = "This is the test description. This is the test description.",
                    priority = Priority.MEDIUM
                ),
                ToDoTask(
                    id = 2,
                    title = "This is the test title.",
                    description = "This is the test description. This is the test description.",
                    priority = Priority.LOW
                )
            ),
            onTaskItemClick = {}
        )
    }
}