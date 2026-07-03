package com.example.diyca.util

import java.io.IOException
import java.net.SocketTimeoutException

suspend fun <T> safeApiCall(call: suspend () -> Resource<T>): Resource<T> {
    return try {
        call()
    } catch (_: SocketTimeoutException) {
        Resource.Error(ErrorType.ServerError)
    }catch (_: IOException) {
        Resource.Error(ErrorType.NetworkError)
    } catch (_: Exception) {
        Resource.Error(ErrorType.Unknown)
    }
}