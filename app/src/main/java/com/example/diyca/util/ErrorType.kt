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
    data object InvalidCredentials : ErrorType() // Неверный логин/пароль
    data object NotFound : ErrorType() // 404 Не найдено

    // Ошибки формата логина или пароля
    data object InvalidEmailFormat : ErrorType() // Некорректный формат почты
    data object InvalidPasswordFormat : ErrorType() // Пароль слишком простой

    // Неизвестная ошибка
    data object Unknown : ErrorType()
}