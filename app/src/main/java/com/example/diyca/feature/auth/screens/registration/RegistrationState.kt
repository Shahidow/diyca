package com.example.diyca.feature.auth.screens.registration

data class RegistrationState(
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val error: Int? = null
)