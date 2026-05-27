package com.example.diyca.data.network

import com.example.diyca.data.dto.auth.requests.RefreshRequest
import com.example.diyca.data.dto.auth.responses.RefreshResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface TokenApi {
    @POST("/auth/refresh")
    suspend fun refreshToken(@Body request: RefreshRequest): Response<RefreshResponse>
}