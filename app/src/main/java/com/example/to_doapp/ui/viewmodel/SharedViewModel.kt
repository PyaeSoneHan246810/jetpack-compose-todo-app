package com.example.to_doapp.ui.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.to_doapp.data.model.ToDoTask
import com.example.to_doapp.data.repository.ToDoRepository
import com.example.to_doapp.ui.tasksList.state.SearchAppBarState
import com.example.to_doapp.util.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val toDoRepository: ToDoRepository
): ViewModel() {
    val searchAppBarState: MutableState<SearchAppBarState> = mutableStateOf(SearchAppBarState.CLOSED)
    val searchQueryState: MutableState<String> = mutableStateOf("")

    private val _allTasksResponse = MutableStateFlow<Response<List<ToDoTask>>>(Response.Idle)
    val allTasksResponse: StateFlow<Response<List<ToDoTask>>> = _allTasksResponse

    fun getAllTasks() {
        _allTasksResponse.value = Response.Loading
        try {
            viewModelScope.launch {
                delay(2000)
                toDoRepository.getAllTasks().collect { allTasks ->
                    _allTasksResponse.value = Response.Success(data = allTasks)
                }
            }
        } catch (e: Exception) {
            _allTasksResponse.value = Response.Error(error = e)
        }
    }
}