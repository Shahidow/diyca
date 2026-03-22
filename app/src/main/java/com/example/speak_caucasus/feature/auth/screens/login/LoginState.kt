package com.example.speak_caucasus.feature.auth.screens.login

data class LoginState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val error: Int? = null
)
