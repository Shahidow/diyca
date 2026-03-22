package com.example.speak_caucasus.data.dto.requests

data class PasswordResetRequest (
    val email: String
)

data class VerifyResetCodeRequest (
    val email: String,
    val code: String
)

data class ResetPasswordRequest (
    val email: String,
    val verificationToken: String,
    val newPassword: String
)