package com.example.to_doapp.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.to_doapp.R
import com.example.to_doapp.ui.theme.Shapes
import com.example.to_doapp.ui.theme.ToDoAppTheme

@Composable
fun DeleteConfirmDialog(
    modifier: Modifier = Modifier,
    title: String,
    message: String,
    openDialog: Boolean,
    onClose: () -> Unit,
    onConfirm: () -> Unit,
) {
    if (openDialog) {
        AlertDialog(
            modifier = modifier,
            containerColor = MaterialTheme.colorScheme.surfaceContainer,
            titleContentColor = MaterialTheme.colorScheme.onSurface,
            textContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
            shape = Shapes.medium,
            title = {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
                )
            },
            text = {
                Text(
                    text = message,
                    style = MaterialTheme.typography.titleMedium
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        onConfirm()
                        onClose()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    )
                ) {
                    Text(text = stringResource(id = R.string.yes))
                }
            },
            dismissButton = {
                OutlinedButton(
                    onClick = {
                        onClose()
                    },
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = MaterialTheme.colorScheme.onSurface
                    ),
                ) {
                    Text(text = stringResource(id = R.string.no))
                }
            },
            onDismissRequest = {
                onClose()
            },
        )
    }
}

@Preview
@Composable
private fun DeleteConfirmDialogPrev() {
    ToDoAppTheme {
        DeleteConfirmDialog(
            title = "Test Title",
            message = "Test Message",
            openDialog = true,
            onClose = {},
            onConfirm = {}
        )
    }
}