package com.example.diyca.data.network

import com.example.diyca.data.dto.requests.RefreshRequest
import com.example.diyca.data.dto.responses.RefreshResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("/auth/refresh")
    suspend fun refreshToken(@Body request: RefreshRequest): Response<RefreshResponse>
}