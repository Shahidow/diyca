package com.example.diyca.util

import java.io.IOException

suspend fun <T> safeApiCall(call: suspend () -> Resource<T>): Resource<T> {
    return try {
        call()
    } catch (_: IOException) {
        Resource.Error(ErrorType.NetworkError)
    } catch (_: Exception) {
        Resource.Error(ErrorType.Unknown)
    }
}