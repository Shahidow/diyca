package com.example.speak_caucasus.data.network

import com.example.speak_caucasus.data.dto.requests.RefreshRequest
import com.example.speak_caucasus.data.dto.responses.RefreshResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("/auth/refresh")
    suspend fun refreshToken(@Body request: RefreshRequest): Response<RefreshResponse>
}