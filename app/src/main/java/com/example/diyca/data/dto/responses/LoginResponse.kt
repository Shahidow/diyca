package com.example.diyca.data.dto.responses

data class LoginResponse(
    val statusCode: String,
    val message: String,
    val data: LoginData
)

data class LoginData(
    val nickname: String,
    val email: String,
    val access: String,
    val refresh: String,
)