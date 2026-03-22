package com.example.diyca.data.repository.auth

import com.example.diyca.util.Resource
import com.example.diyca.domain.auth.models.UserData
import com.example.diyca.domain.auth.models.LoginData
import com.example.diyca.domain.auth.models.RegistrationData
import com.example.diyca.domain.auth.recovery.models.ResetPasswordData
import com.example.diyca.domain.auth.recovery.models.VerifyResetCodeData
import com.example.diyca.domain.home.settings.models.ChangeProfileData
import com.example.diyca.domain.home.settings.models.RemoveProfileData
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