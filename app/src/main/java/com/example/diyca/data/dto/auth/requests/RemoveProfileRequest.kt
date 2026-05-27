package com.example.diyca.data.dto.auth.requests

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RemoveProfileRequest (
    @SerialName("password")
    val password: String,
    @SerialName("refresh")
    val refresh: String
)