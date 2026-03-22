package com.example.diyca.util

sealed class Resource<out T> {
    data class Success<T>(val data: T?) : Resource<T>()
    data class Error(val errorType: ErrorType, val resultCode: Int? = null) : Resource<Nothing>()
}