package com.example.diyca.util

sealed class LoadingStatus {
    data class Progress(val value: Float, val message: String) : LoadingStatus()
    data object Success : LoadingStatus()
    data class Error(val errorType: ErrorType) : LoadingStatus()
}