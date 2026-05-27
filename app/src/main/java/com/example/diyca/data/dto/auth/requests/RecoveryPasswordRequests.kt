package com.example.diyca.data.dto.auth.requests

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