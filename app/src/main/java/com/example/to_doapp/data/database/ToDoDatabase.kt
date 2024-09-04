package com.example.to_doapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.to_doapp.data.model.ToDoTask

@Database(entities = [ToDoTask::class], version = 1, exportSchema = false)
abstract class ToDoDatabase: RoomDatabase() {
    abstract fun ToDoDao(): ToDoDao
}