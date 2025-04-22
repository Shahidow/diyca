package com.example.speak_caucasus.feature.start.screens.login

data class LoginState(
    val phone: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
)
