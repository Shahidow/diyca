package com.example.diyca.data.dto.responses

data class RefreshResponse(
    val statusCode: Int,
    val message: String,
    val data: RefreshData
)

data class RefreshData(
    val access: String,
    val refresh: String,
)