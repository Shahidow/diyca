package com.example.diyca.domain.auth.models

data class UserData(
    val nickname: String,
    val email: String,
    val accessToken: String,
    val refreshToken: String
)
