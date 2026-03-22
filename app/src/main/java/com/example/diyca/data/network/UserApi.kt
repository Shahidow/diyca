package com.example.diyca.data.network

import com.example.diyca.data.dto.requests.ChangeProfileRequest
import com.example.diyca.data.dto.requests.LoginRequest
import com.example.diyca.data.dto.requests.PasswordResetRequest
import com.example.diyca.data.dto.responses.LoginResponse
import com.example.diyca.data.dto.requests.RegistrationRequest
import com.example.diyca.data.dto.requests.RemoveProfileRequest
import com.example.diyca.data.dto.requests.ResetPasswordRequest
import com.example.diyca.data.dto.requests.VerifyResetCodeRequest
import com.example.diyca.data.dto.responses.VerifyResetCodeResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.HTTP
import retrofit2.http.PATCH
import retrofit2.http.POST

interface UserApi {
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