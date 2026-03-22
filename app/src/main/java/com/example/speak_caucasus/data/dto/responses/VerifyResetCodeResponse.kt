package com.example.speak_caucasus.data.dto.responses

data class VerifyResetCodeResponse(
    val data: PasswordRecoveryToken
)

data class PasswordRecoveryToken (
    val verificationToken: String
)
