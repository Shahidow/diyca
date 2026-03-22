package com.example.speak_caucasus.data.mappers

import com.example.speak_caucasus.data.dto.requests.ChangeProfileRequest
import com.example.speak_caucasus.data.dto.requests.LoginRequest
import com.example.speak_caucasus.data.dto.requests.RegistrationRequest
import com.example.speak_caucasus.data.dto.requests.RemoveProfileRequest
import com.example.speak_caucasus.data.dto.requests.ResetPasswordRequest
import com.example.speak_caucasus.domain.auth.models.LoginData
import com.example.speak_caucasus.domain.auth.models.RegistrationData
import com.example.speak_caucasus.domain.auth.recovery.models.ResetPasswordData
import com.example.speak_caucasus.domain.home.settings.models.ChangeProfileData
import com.example.speak_caucasus.domain.home.settings.models.RemoveProfileData

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
            refreshToken = removeProfileData.refreshToken
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