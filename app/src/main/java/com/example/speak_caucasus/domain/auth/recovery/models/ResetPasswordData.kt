package com.example.speak_caucasus.domain.auth.recovery.models

data class ResetPasswordData(
    val email: String,
    val verificationToken: String,
    val newPassword: String
)
