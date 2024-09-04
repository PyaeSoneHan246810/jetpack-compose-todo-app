package com.example.to_doapp.ui.tasksList.screen

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.to_doapp.ui.tasksList.component.AddItemFab
import com.example.to_doapp.ui.tasksList.component.MainAppbar
import com.example.to_doapp.ui.tasksList.state.SearchAppBarState
import com.example.to_doapp.ui.theme.ToDoAppTheme

@Composable
fun TasksListScreen(
    modifier: Modifier = Modifier,
    navigateToTaskScreen: (taskId: Int) -> Unit,
    searchAppBarState: SearchAppBarState,
    searchQuery: String,
    onSearchActionClick: () -> Unit,
    onSearchQueryChange: (newQuery: String) -> Unit,
    onSearchAppBarClose: () -> Unit,
) {
    Scaffold(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            MainAppbar(
                searchAppBarState = searchAppBarState,
                searchQuery = searchQuery,
                onSearchActionClick = onSearchActionClick,
                onSearchQueryChange = onSearchQueryChange,
                onSearchAppBarClose = onSearchAppBarClose,
            )
        },
        floatingActionButton = {
            AddItemFab(
                onClick = {
                    navigateToTaskScreen(-1)
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {

        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, name = "Light Mode", showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark Mode", showBackground = true)
@Composable
private fun TasksListScreenPrev() {
    ToDoAppTheme {
        TasksListScreen(
            navigateToTaskScreen = {},
            searchAppBarState = SearchAppBarState.CLOSED,
            searchQuery = "",
            onSearchActionClick = {},
            onSearchQueryChange = {},
            onSearchAppBarClose = {}
        )
    }
}