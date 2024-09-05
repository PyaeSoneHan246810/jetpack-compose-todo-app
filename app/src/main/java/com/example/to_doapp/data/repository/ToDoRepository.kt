package com.example.to_doapp.data.repository

import com.example.to_doapp.data.database.ToDoDao
import com.example.to_doapp.data.model.ToDoTask
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ViewModelScoped
class ToDoRepository @Inject constructor(
    private val todoDao: ToDoDao
) {
    fun getAllTasks(): Flow<List<ToDoTask>> = todoDao.getAllTasks()

    fun getSelectedTask(taskId: Int): Flow<ToDoTask?> = todoDao.getSelectedTask(taskId)

    suspend fun addNewTask(toDoTask: ToDoTask) = todoDao.addNewTask(toDoTask)

    suspend fun updateTask(toDoTask: ToDoTask) = todoDao.updateTask(toDoTask)

    suspend fun deleteTask(toDoTask: ToDoTask) = todoDao.deleteTask(toDoTask)

    suspend fun deleteAllTasks() = todoDao.deleteAllTasks()

    fun searchTasks(searchQuery: String): Flow<List<ToDoTask>> = todoDao.searchTasks(searchQuery)

    fun sortByLowPriority(): Flow<List<ToDoTask>> = todoDao.sortByLowPriority()
    fun sortByHighPriority(): Flow<List<ToDoTask>> = todoDao.sortByHighPriority()

}