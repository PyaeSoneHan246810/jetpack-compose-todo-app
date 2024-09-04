package com.example.to_doapp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.to_doapp.data.models.ToDoTask

@Database(entities = [ToDoTask::class], version = 1, exportSchema = false)
abstract class ToDoDatabase: RoomDatabase() {
    abstract fun ToDoDao(): ToDoDao
}