package com.example.speak_caucasus.data.repository.auth

import com.example.speak_caucasus.util.Resource
import com.example.speak_caucasus.domain.auth.models.UserData
import com.example.speak_caucasus.domain.auth.models.LoginData
import com.example.speak_caucasus.domain.auth.models.RegistrationData
import com.example.speak_caucasus.domain.auth.recovery.models.ResetPasswordData
import com.example.speak_caucasus.domain.auth.recovery.models.VerifyResetCodeData
import com.example.speak_caucasus.domain.home.settings.models.ChangeProfileData
import com.example.speak_caucasus.domain.home.settings.models.RemoveProfileData
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun login(loginData: LoginData): Flow<Resource<UserData>>
    fun registration(registrationData: RegistrationData): Flow<Resource<Unit>>
    fun removeProfile(removeProfileData: RemoveProfileData): Flow<Resource<Unit>>
    fun changeProfile(changeProfileData: ChangeProfileData): Flow<Resource<Unit>>
    fun passwordReset(email: String): Flow<Resource<Unit>>
    fun verifyResetCode(email: String, code: String): Flow<Resource<VerifyResetCodeData>>
    fun resetPassword(resetPasswordData: ResetPasswordData): Flow<Resource<Unit>>
}