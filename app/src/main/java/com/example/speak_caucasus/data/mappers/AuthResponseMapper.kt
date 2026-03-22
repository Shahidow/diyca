package com.example.speak_caucasus.data.mappers

import com.example.speak_caucasus.data.dto.responses.LoginResponse
import com.example.speak_caucasus.data.dto.responses.VerifyResetCodeResponse
import com.example.speak_caucasus.domain.auth.models.UserData
import com.example.speak_caucasus.domain.auth.recovery.models.VerifyResetCodeData

class AuthResponseMapper {
    fun loginResponseToDomain(loginResponse: LoginResponse): UserData {
        return UserData(
            nickname = loginResponse.data.nickname,
            email = loginResponse.data.email,
            accessToken = loginResponse.data.access,
            refreshToken = loginResponse.data.refresh
        )
    }

    fun verifyResetCodeToDomain(verifyResetCodeResponse: VerifyResetCodeResponse): VerifyResetCodeData {
        return VerifyResetCodeData(
            verificationToken = verifyResetCodeResponse.data.verificationToken
        )
    }
}