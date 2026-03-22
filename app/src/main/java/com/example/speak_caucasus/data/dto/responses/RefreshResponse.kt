package com.example.speak_caucasus.data.dto.responses

data class RefreshResponse(
    val statusCode: Int,
    val message: String,
    val data: RefreshData
)

data class RefreshData(
    val access: String,
    val refresh: String,
)