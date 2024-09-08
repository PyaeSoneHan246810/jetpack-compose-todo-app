package com.example.to_doapp.ui.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.to_doapp.data.model.Priority
import com.example.to_doapp.data.model.ToDoTask
import com.example.to_doapp.data.model.toPriority
import com.example.to_doapp.data.repository.DataStoreRepository
import com.example.to_doapp.data.repository.ToDoRepository
import com.example.to_doapp.ui.tasksList.state.SearchAppBarState
import com.example.to_doapp.util.Action
import com.example.to_doapp.util.Constants
import com.example.to_doapp.util.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val toDoRepository: ToDoRepository,
    private val dataStoreRepository: DataStoreRepository
): ViewModel() {

    // Search states
    val searchAppBarState: MutableState<SearchAppBarState> = mutableStateOf(SearchAppBarState.CLOSED)
    val searchQueryState: MutableState<String> = mutableStateOf("")

    // Task states
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

    fun validateFields(): Boolean {
        return taskTitle.value.isNotEmpty() && taskDesc.value.isNotEmpty()
    }

    // Get all tasks
    private val _allTasksResponse = MutableStateFlow<Response<List<ToDoTask>>>(Response.Idle)
    val allTasksResponse: StateFlow<Response<List<ToDoTask>>> = _allTasksResponse

    private fun getAllTasks() {
        _allTasksResponse.value = Response.Loading
        try {
            viewModelScope.launch(Dispatchers.IO) {
                toDoRepository.getAllTasks().collect { allTasks ->
                    _allTasksResponse.value = Response.Success(data = allTasks)
                }
            }
        } catch (e: Exception) {
            _allTasksResponse.value = Response.Error(error = e)
        }
    }

    // Get selected task
    private val _selectedTask = MutableStateFlow<ToDoTask?>(null)
    val selectedTask: StateFlow<ToDoTask?> = _selectedTask

    fun getSelectedTask(taskId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            toDoRepository.getSelectedTask(taskId = taskId).collect { task ->
                _selectedTask.value = task
            }
        }
    }

    // Get search tasks
    private val _searchTasksResponse = MutableStateFlow<Response<List<ToDoTask>>>(Response.Idle)
    val searchTasksResponse: StateFlow<Response<List<ToDoTask>>> = _searchTasksResponse

    fun getSearchTasks(searchQuery: String) {
        _searchTasksResponse.value = Response.Loading
        try {
            viewModelScope.launch(Dispatchers.IO) {
                toDoRepository.searchTasks("%$searchQuery%").collect {  searchTasks ->
                    _searchTasksResponse.value = Response.Success(data = searchTasks)
                }
            }
        } catch (e: Exception) {
            _searchTasksResponse.value = Response.Error(error = e)
        }
        searchAppBarState.value = SearchAppBarState.TRIGGERED
    }

    // Get tasks by low priority
    val lowPriorityTasks: StateFlow<List<ToDoTask>> = toDoRepository.sortByLowPriority().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        emptyList()
    )

    // Get tasks by high priority
    val highPriorityTasks: StateFlow<List<ToDoTask>> = toDoRepository.sortByHighPriority().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        emptyList()
    )

    // Database action
    val action: MutableState<Action> = mutableStateOf(Action.NO_ACTION)

    // Handle database action with operation
    fun handleDatabaseAction(action: Action) {
        when(action) {
            Action.ADD -> {
                addTask()
            }
            Action.UPDATE -> {
                updateTask()
            }
            Action.DELETE -> {
                deleteTask()
            }
            Action.DELETE_ALL -> {
                deleteAllTasks()
            }
            Action.UNDO -> {
                addTask()
            }
            Action.NO_ACTION -> {}
        }
        resetDatabaseAction()
    }

    fun resetDatabaseAction() {
        this.action.value = Action.NO_ACTION
    }

    // Database operations
    private fun addTask() {
        viewModelScope.launch(Dispatchers.IO) {
            val newTask = ToDoTask(
                title = taskTitle.value,
                description = taskDesc.value,
                priority = taskPriority.value
            )
            toDoRepository.addNewTask(newTask)
        }
        searchAppBarState.value = SearchAppBarState.CLOSED
    }

    private fun updateTask() {
        viewModelScope.launch(Dispatchers.IO) {
            val updatedTask = ToDoTask(
                id = taskId.value,
                title = taskTitle.value,
                description = taskDesc.value,
                priority = taskPriority.value
            )
            toDoRepository.updateTask(updatedTask)
        }
    }

    private fun deleteTask() {
        viewModelScope.launch(Dispatchers.IO) {
            val taskToDelete = ToDoTask(
                id = taskId.value,
                title = taskTitle.value,
                description = taskDesc.value,
                priority = taskPriority.value
            )
            toDoRepository.deleteTask(taskToDelete)
        }
    }

    private fun deleteAllTasks() {
        viewModelScope.launch(Dispatchers.IO) {
            toDoRepository.deleteAllTasks()
        }
    }

    // Save and read sort state
    private val _sortStateResponse = MutableStateFlow<Response<Priority>>(Response.Idle)
    val sortStateResponse: StateFlow<Response<Priority>> = _sortStateResponse

    fun saveSortState(priority: Priority) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.saveSortState(priority.title)
        }
    }

    private fun readSortState() {
        _sortStateResponse.value = Response.Loading
        try {
            viewModelScope.launch(Dispatchers.IO) {
                dataStoreRepository.readSortState().collect { sortState ->
                    _sortStateResponse.value = Response.Success(data = sortState.toPriority())
                }
            }
        } catch (e: Exception) {
            _sortStateResponse.value = Response.Error(error = e)
        }
    }

    // init
    init {
        readSortState()
        getAllTasks()
    }
}