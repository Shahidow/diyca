package com.example.diyca.data.mappers

import com.example.diyca.data.dto.responses.LoginResponse
import com.example.diyca.data.dto.responses.VerifyResetCodeResponse
import com.example.diyca.domain.auth.models.UserData
import com.example.diyca.domain.auth.recovery.models.VerifyResetCodeData

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