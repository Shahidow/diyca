package com.example.diyca.data.repository.auth

import com.example.diyca.util.Resource
import com.example.diyca.domain.auth.models.UserData
import com.example.diyca.domain.auth.models.LoginData
import com.example.diyca.domain.auth.models.RegistrationData
import com.example.diyca.domain.auth.recovery.models.ResetPasswordData
import com.example.diyca.domain.auth.recovery.models.VerifyResetCodeData
import com.example.diyca.domain.home.settings.models.ChangeProfileData
import com.example.diyca.domain.home.settings.models.RemoveProfileData

interface AuthRepository {
    suspend fun login(loginData: LoginData): Resource<UserData>
    suspend fun registration(registrationData: RegistrationData): Resource<Unit>
    suspend fun removeProfile(removeProfileData: RemoveProfileData): Resource<Unit>
    suspend fun changeProfile(changeProfileData: ChangeProfileData): Resource<Unit>
    suspend fun passwordReset(email: String): Resource<Unit>
    suspend fun verifyResetCode(email: String, code: String): Resource<VerifyResetCodeData>
    suspend fun resetPassword(resetPasswordData: ResetPasswordData): Resource<Unit>
}