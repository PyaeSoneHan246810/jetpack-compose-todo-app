package com.example.to_doapp.ui.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.to_doapp.data.model.Priority
import com.example.to_doapp.data.model.ToDoTask
import com.example.to_doapp.data.repository.ToDoRepository
import com.example.to_doapp.ui.tasksList.state.SearchAppBarState
import com.example.to_doapp.util.Constants
import com.example.to_doapp.util.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val toDoRepository: ToDoRepository
): ViewModel() {

    val taskId: MutableState<Int> = mutableIntStateOf(0)
    val taskTitle: MutableState<String> = mutableStateOf("")
    val taskDesc: MutableState<String> = mutableStateOf("")
    val taskPriority: MutableState<Priority> = mutableStateOf(Priority.LOW)

    fun updateTaskProperties(selectedTask: ToDoTask?) {
        if (selectedTask != null) {
            taskId.value = selectedTask.id
            taskTitle.value = selectedTask.title
            taskDesc.value = selectedTask.description
            taskPriority.value = selectedTask.priority
        } else {
            taskId.value = 0
            taskTitle.value = ""
            taskDesc.value = ""
            taskPriority.value = Priority.LOW
        }
    }

    fun updateTitle(newTitle: String) {
        if (newTitle.length < Constants.MAX_TITLE_LENGTH) {
            taskTitle.value = newTitle
        }
    }

    val searchAppBarState: MutableState<SearchAppBarState> = mutableStateOf(SearchAppBarState.CLOSED)
    val searchQueryState: MutableState<String> = mutableStateOf("")

    private val _allTasksResponse = MutableStateFlow<Response<List<ToDoTask>>>(Response.Idle)
    val allTasksResponse: StateFlow<Response<List<ToDoTask>>> = _allTasksResponse

    fun getAllTasks() {
        _allTasksResponse.value = Response.Loading
        try {
            viewModelScope.launch {
                toDoRepository.getAllTasks().collect { allTasks ->
                    _allTasksResponse.value = Response.Success(data = allTasks)
                }
            }
        } catch (e: Exception) {
            _allTasksResponse.value = Response.Error(error = e)
        }
    }

    private val _selectedTask = MutableStateFlow<ToDoTask?>(null)
    val selectedTask: StateFlow<ToDoTask?> = _selectedTask

    fun getSelectedTask(taskId: Int) {
        viewModelScope.launch {
            toDoRepository.getSelectedTask(taskId = taskId).collect { task ->
                _selectedTask.value = task
            }
        }
    }
}