package com.example.diyca.domain.auth.recovery.models

data class ResetPasswordData(
    val email: String,
    val verificationToken: String,
    val newPassword: String
)
