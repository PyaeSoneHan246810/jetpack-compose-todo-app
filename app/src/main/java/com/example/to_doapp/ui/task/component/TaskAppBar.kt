package com.example.to_doapp.ui.task.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.example.to_doapp.R
import com.example.to_doapp.components.DeleteConfirmDialog
import com.example.to_doapp.data.model.ToDoTask
import com.example.to_doapp.ui.theme.ToDoAppTheme
import com.example.to_doapp.util.Action

@Composable
fun TaskAppBar(
    modifier: Modifier = Modifier,
    selectedTask: ToDoTask?,
    navigateToTasksListScreen: (action: Action) -> Unit
) {
    if (selectedTask == null) {
        NewTaskAppBar(
            modifier = modifier,
            onBackClick = navigateToTasksListScreen,
            onSaveClick = navigateToTasksListScreen,
        )
    } else {
        ExistingTaskAppBar(
            modifier = modifier,
            taskTitle = selectedTask.title,
            onCloseClick = navigateToTasksListScreen,
            onDeleteClick = navigateToTasksListScreen,
            onUpdateClick = navigateToTasksListScreen,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExistingTaskAppBar(
    modifier: Modifier = Modifier,
    taskTitle: String,
    onCloseClick: (action: Action) -> Unit,
    onDeleteClick: (action: Action) -> Unit,
    onUpdateClick: (action: Action) -> Unit,
) {
    var openDialog by remember {
        mutableStateOf(false)
    }
    TopAppBar(
        modifier = modifier
            .fillMaxWidth(),
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
            actionIconContentColor = MaterialTheme.colorScheme.onPrimary,
        ),
        navigationIcon = {
            IconButton(
                onClick = {
                    onCloseClick(Action.NO_ACTION)
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = stringResource(id = R.string.close)
                )
            }
        },
        title = {
            Text(
                text = taskTitle,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        },
        actions = {
            IconButton(
                onClick = {
                    openDialog = true
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = stringResource(id = R.string.delete_task)
                )
            }
            IconButton(
                onClick = {
                    onUpdateClick(Action.UPDATE)
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Save,
                    contentDescription = stringResource(id = R.string.update_task)
                )
            }
            DeleteConfirmDialog(
                title = stringResource(id = R.string.delete_task),
                message = stringResource(id = R.string.delete_confirm),
                openDialog = openDialog,
                onClose = {
                    openDialog = false
                },
                onConfirm = {
                    onDeleteClick(Action.DELETE)
                }
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewTaskAppBar(
    modifier: Modifier = Modifier,
    onBackClick: (action: Action) -> Unit,
    onSaveClick: (action: Action) -> Unit,
) {
    TopAppBar(
        modifier = modifier
            .fillMaxWidth(),
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
            actionIconContentColor = MaterialTheme.colorScheme.onPrimary,
        ),
        navigationIcon = {
            IconButton(
                onClick = {
                    onBackClick(Action.NO_ACTION)
                }
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(id = R.string.back)
                )
            }
        },
        title = { 
            Text(
                text = stringResource(id = R.string.add_task)
            )
        },
        actions = {
            IconButton(
                onClick = {
                    onSaveClick(Action.ADD)
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Check,
                    contentDescription = stringResource(id = R.string.add_task)
                )
            }
        }
    )
}

@Preview
@Composable
private fun ExistingTaskAppBarPrev() {
    ToDoAppTheme {
        ExistingTaskAppBar(
            taskTitle = "Test Task Title",
            onCloseClick = {},
            onDeleteClick = {},
            onUpdateClick = {}
        )
    }
}

@Preview
@Composable
private fun NewTaskAppBarPrev() {
    ToDoAppTheme {
        NewTaskAppBar(
            onBackClick = {},
            onSaveClick = {}
        )
    }
}