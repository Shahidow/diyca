package com.example.diyca.util

import java.io.IOException

suspend fun <T> safeApiCall(call: suspend () -> Resource<T>): Resource<T> {
    return try {
        call()
    } catch (e: IOException) {
        Resource.Error(ErrorType.NetworkError)
    } catch (e: Exception) {
        Resource.Error(ErrorType.Unknown)
    }
}