package com.example.to_doapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.to_doapp.util.Constants

@Entity(tableName = Constants.TODO_TABLE_NAME)
data class ToDoTask(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val description: String,
    val priority: Priority,
)
