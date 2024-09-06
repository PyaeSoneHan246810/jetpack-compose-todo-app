package com.example.to_doapp.util

object Constants {
    const val TODO_TABLE_NAME = "todo_table"
    const val TODO_DATABASE_NAME = "todo_database"

    const val TASKS_LIST_ROUTE_ARG1 = "action"
    const val TASK_ROUTE_ARG1 = "id"

    const val TASKS_LIST_ROUTE = "task_list/{$TASKS_LIST_ROUTE_ARG1}"
    const val TASK_ROUTE = "task/{$TASK_ROUTE_ARG1}"

    const val MAX_TITLE_LENGTH = 20

    const val PREFERENCES_NAME = "todo_preferences"
    const val SORT_STATE_KEY = "sort_state"
}