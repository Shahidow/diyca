package com.example.diyca.data.dto.requests

data class RemoveProfileRequest (
    val password: String,
    val refreshToken: String
)