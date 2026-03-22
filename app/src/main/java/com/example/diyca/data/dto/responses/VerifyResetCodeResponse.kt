package com.example.diyca.data.dto.responses

data class VerifyResetCodeResponse(
    val data: PasswordRecoveryToken
)

data class PasswordRecoveryToken (
    val verificationToken: String
)
