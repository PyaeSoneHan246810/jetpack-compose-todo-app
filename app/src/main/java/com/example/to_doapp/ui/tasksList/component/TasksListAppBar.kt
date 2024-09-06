package com.example.to_doapp.ui.tasksList.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import com.example.to_doapp.R
import com.example.to_doapp.components.PriorityItem
import com.example.to_doapp.data.model.Priority
import com.example.to_doapp.ui.tasksList.state.SearchAppBarState
import com.example.to_doapp.ui.theme.TOP_APP_BAR_HEIGHT
import com.example.to_doapp.ui.theme.ToDoAppTheme

@Composable
fun TasksListAppBar(
    modifier: Modifier = Modifier,
    searchAppBarState: SearchAppBarState,
    searchQuery: String,
    onSearchActionClick: () -> Unit,
    onSearchQueryChange: (newQuery: String) -> Unit,
    onSearchAppBarClose: () -> Unit,
    onSearch: (searchQuery: String) -> Unit,
) {
    when(searchAppBarState) {
        SearchAppBarState.CLOSED -> DefaultAppBar(
            modifier = modifier,
            onSearchActionClick = onSearchActionClick,
            onSortActionClick = { priority ->

            },
            onDeleteAllActionClick = {

            }
        )
        else -> SearchAppBar(
            modifier = modifier,
            value = searchQuery,
            onValueChange = onSearchQueryChange,
            onSearch = onSearch,
            onCloseClick = onSearchAppBarClose
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultAppBar(
    modifier: Modifier = Modifier,
    onSearchActionClick: () -> Unit,
    onSortActionClick: (priority: Priority) -> Unit,
    onDeleteAllActionClick: () -> Unit,
) {
    var isSortMenuExpanded by remember {
        mutableStateOf(false)
    }
    var isMoreOptionsMenuExpanded by remember {
        mutableStateOf(false)
    }
    TopAppBar(
        modifier = modifier
            .fillMaxWidth(),
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
            actionIconContentColor = MaterialTheme.colorScheme.onPrimary,
        ),
        title = {
            Text(text = stringResource(id = R.string.default_app_bar_title))
        },
        actions = {
            //search icon button
            IconButton(
                onClick = onSearchActionClick
            ) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = stringResource(id = R.string.search_tasks)
                )
            }
            //sort icon button
            IconButton(
                onClick = {
                    isSortMenuExpanded = true
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.FilterList,
                    contentDescription = stringResource(id = R.string.sort_tasks)
                )
                DropdownMenu(
                    modifier = Modifier
                        .background(color = MaterialTheme.colorScheme.surface),
                    expanded = isSortMenuExpanded,
                    onDismissRequest = {
                        isSortMenuExpanded = false
                    }
                ) {
                    DropdownMenuItem(
                        text = {
                            PriorityItem(priority = Priority.LOW)
                        },
                        onClick = {
                            isSortMenuExpanded = false
                            onSortActionClick(Priority.LOW)
                        }
                    )
                    DropdownMenuItem(
                        text = {
                            PriorityItem(priority = Priority.MEDIUM)
                        },
                        onClick = {
                            isSortMenuExpanded = false
                            onSortActionClick(Priority.MEDIUM)
                        }
                    )
                    DropdownMenuItem(
                        text = {
                            PriorityItem(priority = Priority.HIGH)
                        },
                        onClick = {
                            isSortMenuExpanded = false
                            onSortActionClick(Priority.HIGH)
                        }
                    )
                }
            }
            //more icon button
            IconButton(
                onClick = {
                    isMoreOptionsMenuExpanded = true
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.MoreVert,
                    contentDescription = stringResource(id = R.string.more_options)
                )
                DropdownMenu(
                    modifier = Modifier
                        .background(color = MaterialTheme.colorScheme.surface),
                    expanded = isMoreOptionsMenuExpanded,
                    onDismissRequest = {
                        isMoreOptionsMenuExpanded = false
                    },
                ) {
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = stringResource(id = R.string.delete_all),
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        },
                        onClick = onDeleteAllActionClick
                    )
                }
            }
        }
    )
}

@Composable
fun SearchAppBar(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (newValue: String) -> Unit,
    onSearch: (searchQuery: String) -> Unit,
    onCloseClick: () -> Unit,
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(TOP_APP_BAR_HEIGHT),
        color = MaterialTheme.colorScheme.primary,
    ) {
        TextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = value,
            onValueChange = onValueChange,
            textStyle = MaterialTheme.typography.titleMedium,
            leadingIcon = {
                IconButton(
                    onClick = {},
                    enabled = false
                ) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = stringResource(id = R.string.search_tasks)
                    )
                }
            },
            placeholder = {
                Text(
                    text = stringResource(id = R.string.search_query_placeholder),
                    style = MaterialTheme.typography.titleMedium
                )
            },
            trailingIcon = {
                IconButton(
                    onClick = {
                        onCloseClick()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Cancel,
                        contentDescription = stringResource(id = R.string.cancel)
                    )
                }
            },
            singleLine = true,
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = MaterialTheme.colorScheme.primary,
                unfocusedLeadingIconColor = MaterialTheme.colorScheme.onPrimary,
                unfocusedTrailingIconColor = MaterialTheme.colorScheme.onPrimary,
                unfocusedPlaceholderColor = MaterialTheme.colorScheme.outlineVariant,
                unfocusedTextColor = MaterialTheme.colorScheme.onPrimary,
                focusedContainerColor = MaterialTheme.colorScheme.primary,
                focusedLeadingIconColor = MaterialTheme.colorScheme.onPrimary,
                focusedTrailingIconColor = MaterialTheme.colorScheme.onPrimary,
                focusedPlaceholderColor = MaterialTheme.colorScheme.outlineVariant,
                focusedTextColor = MaterialTheme.colorScheme.onPrimary,
                cursorColor = MaterialTheme.colorScheme.onPrimary,
            ),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    val searchQuery = value.trim()
                    onSearch(searchQuery)
                }
            )
        )
    }
}

@Preview
@Composable
private fun DefaultAppBarPrev() {
    ToDoAppTheme {
        DefaultAppBar(
            onSearchActionClick = {},
            onSortActionClick = {},
            onDeleteAllActionClick = {}
        )
    }
}

@Preview
@Composable
private fun SearchAppBarPrev() {
    ToDoAppTheme {
        SearchAppBar(
            value = "",
            onValueChange ={},
            onSearch = {},
            onCloseClick = {}
        )
    }
}