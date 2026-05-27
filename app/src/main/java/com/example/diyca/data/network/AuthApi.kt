package com.example.diyca.data.network

import com.example.diyca.data.dto.auth.requests.ChangeProfileRequest
import com.example.diyca.data.dto.auth.requests.LoginRequest
import com.example.diyca.data.dto.auth.requests.PasswordResetRequest
import com.example.diyca.data.dto.auth.responses.LoginResponse
import com.example.diyca.data.dto.auth.requests.RegistrationRequest
import com.example.diyca.data.dto.auth.requests.RemoveProfileRequest
import com.example.diyca.data.dto.auth.requests.ResetPasswordRequest
import com.example.diyca.data.dto.auth.requests.VerifyResetCodeRequest
import com.example.diyca.data.dto.auth.responses.VerifyResetCodeResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.HTTP
import retrofit2.http.PATCH
import retrofit2.http.POST

interface AuthApi {
    @POST("/auth/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>

    @POST("/auth/register")
    suspend fun registration(@Body registrationRequest: RegistrationRequest): Response<Unit>

    @HTTP(method = "DELETE", path = "/auth/remove-profile", hasBody = true)
    suspend fun removeProfile(@Body removeProfileRequest: RemoveProfileRequest):Response<Unit>

    @PATCH("/auth/change-profile")
    suspend fun changeProfile(@Body request: ChangeProfileRequest): Response<Unit>

    @POST("/auth/forgot-password")
    suspend fun passwordReset(@Body passwordResetRequest: PasswordResetRequest): Response<Unit>

    @POST("/auth/forgot-password/confirm")
    suspend fun verifyResetCode(@Body verifyResetCodeRequest: VerifyResetCodeRequest): Response<VerifyResetCodeResponse>

    @POST("/auth/forgot-password/reset")
    suspend fun resetPassword(@Body resetPasswordRequest: ResetPasswordRequest): Response<Unit>
}