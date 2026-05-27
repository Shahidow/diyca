package com.example.diyca.util

import retrofit2.Response

fun <T> handleNetworkError(response: Response<*>): Resource<T> {
    return when (val code = response.code()) {
        401 -> Resource.Error(ErrorType.Unauthorized, code)
        403 -> Resource.Error(ErrorType.Forbidden, code)
        404 -> Resource.Error(ErrorType.NotFound, code)
        409 -> Resource.Error(ErrorType.EmailAlreadyExists, code)
        in 500..599 -> Resource.Error(ErrorType.ServerError, code)
        else -> Resource.Error(ErrorType.Unknown, code)
    }
}