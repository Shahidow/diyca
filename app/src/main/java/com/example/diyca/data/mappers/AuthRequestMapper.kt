package com.example.diyca.data.mappers

import com.example.diyca.data.dto.auth.requests.ChangeProfileRequest
import com.example.diyca.data.dto.auth.requests.LoginRequest
import com.example.diyca.data.dto.auth.requests.RegistrationRequest
import com.example.diyca.data.dto.auth.requests.RemoveProfileRequest
import com.example.diyca.data.dto.auth.requests.ResetPasswordRequest
import com.example.diyca.domain.auth.models.LoginData
import com.example.diyca.domain.auth.models.RegistrationData
import com.example.diyca.domain.auth.recovery.models.ResetPasswordData
import com.example.diyca.domain.home.settings.models.ChangeProfileData
import com.example.diyca.domain.home.settings.models.RemoveProfileData

class AuthRequestMapper {
    fun loginRequestToData(loginData: LoginData): LoginRequest {
        return LoginRequest(
            email = loginData.login,
            password = loginData.password
        )
    }

    fun registrationRequestToData(registrationData: RegistrationData): RegistrationRequest {
        return RegistrationRequest(
            nickname = registrationData.name,
            email = registrationData.email,
            password = registrationData.password,
        )
    }

    fun removeProfileRequestToData(removeProfileData: RemoveProfileData): RemoveProfileRequest {
        return RemoveProfileRequest(
            password = removeProfileData.password,
            refresh = removeProfileData.refreshToken
        )
    }

    fun changeProfileRequestToData(changeProfileData: ChangeProfileData): ChangeProfileRequest {
        return ChangeProfileRequest(
            newNickname = changeProfileData.newNickname,
            currentPassword = changeProfileData.currentPassword,
            newPassword = changeProfileData.newPassword
        )
    }

    fun resetPasswordRequestToData(resetPasswordData: ResetPasswordData): ResetPasswordRequest {
        return ResetPasswordRequest(
            email = resetPasswordData.email,
            verificationToken = resetPasswordData.verificationToken,
            newPassword = resetPasswordData.newPassword
        )
    }
}