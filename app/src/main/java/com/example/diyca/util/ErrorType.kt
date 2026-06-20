package com.example.diyca.util

sealed class ErrorType {
    // Ошибки сети и сервера
    data object NetworkError : ErrorType()
    data object ServerError : ErrorType()  // 5xx
    data object Unauthorized : ErrorType() // 401

    // Специфичные ошибки API
    data object InvalidRequest : ErrorType() // 400 Неверные или недопустимые данные запроса
    data object EmailAlreadyExists : ErrorType() // 409 Почта уже используется
    data object Forbidden : ErrorType() // 403 Запрещено
    data object NotFound : ErrorType() // 404 Не найдено

    // Неизвестная ошибка
    data object Unknown : ErrorType()
}