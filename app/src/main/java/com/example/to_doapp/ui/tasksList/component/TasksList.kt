package com.example.to_doapp.ui.tasksList.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.to_doapp.R
import com.example.to_doapp.data.model.Priority
import com.example.to_doapp.data.model.ToDoTask
import com.example.to_doapp.ui.theme.MEDIUM_PADDING
import com.example.to_doapp.ui.theme.ToDoAppTheme
import com.example.to_doapp.util.Action
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TasksList(
    modifier: Modifier = Modifier,
    tasks: List<ToDoTask>,
    onTaskItemClick: (taskId: Int) -> Unit,
    onSwipeToDelete: (action: Action, task: ToDoTask) -> Unit
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
                val scope = rememberCoroutineScope()
                var isDismissed by remember {
                    mutableStateOf(false)
                }
                val swipeToDismissBoxState = rememberSwipeToDismissBoxState(
                    initialValue = SwipeToDismissBoxValue.Settled,
                    confirmValueChange = { value ->
                        if (value == SwipeToDismissBoxValue.EndToStart) {
                            isDismissed = true
                            scope.launch {
                                delay(300)
                                onSwipeToDelete(Action.DELETE, task)
                            }
                            true
                        } else {
                            false
                        }
                    },
                    positionalThreshold = { totalDistance ->
                        return@rememberSwipeToDismissBoxState totalDistance * 0.25f
                    }
                )
                val iconDegree by animateFloatAsState(
                    targetValue = if (swipeToDismissBoxState.targetValue == SwipeToDismissBoxValue.EndToStart) -45f else 0f,
                    label = stringResource(id = R.string.icon_degree)
                )
                var itemVisible by remember {
                    mutableStateOf(false)
                }
                LaunchedEffect(key1 = true) {
                    itemVisible = true
                }
                AnimatedVisibility(
                    visible = itemVisible && !isDismissed,
                    enter = expandVertically(
                        animationSpec = tween(
                            durationMillis = 300
                        )
                    ),
                    exit = shrinkVertically(
                        animationSpec = tween(
                            durationMillis = 300
                        )
                    )
                ) {
                    SwipeToDismissBox(
                        modifier = Modifier,
                        state = swipeToDismissBoxState,
                        enableDismissFromEndToStart = true,
                        enableDismissFromStartToEnd = false,
                        backgroundContent = {
                            SwipeToDismissTaskBackground(
                                degree = iconDegree
                            )
                        },
                    ) {
                        TaskItem(
                            toDoTask = task,
                            onTaskItemClick = onTaskItemClick
                        )
                    }
                }
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
            onTaskItemClick = {},
            onSwipeToDelete = { action, taskId ->

            }
        )
    }
}