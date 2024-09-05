package com.example.to_doapp.util

sealed class Response<out T> {
    data object Idle: Response<Nothing>()
    data object Loading: Response<Nothing>()
    data class Success<T>(val data: T): Response<T>()
    data class Error(val error: Throwable): Response<Nothing>()
}