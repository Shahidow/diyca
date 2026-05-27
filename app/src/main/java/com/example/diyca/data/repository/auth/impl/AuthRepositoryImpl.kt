package com.example.diyca.data.repository.auth.impl

import com.example.diyca.data.dto.auth.requests.PasswordResetRequest
import com.example.diyca.data.dto.auth.requests.VerifyResetCodeRequest
import com.example.diyca.data.mappers.AuthRequestMapper
import com.example.diyca.data.mappers.AuthResponseMapper
import com.example.diyca.data.network.AuthApi
import com.example.diyca.util.Resource
import com.example.diyca.data.repository.auth.AuthRepository
import com.example.diyca.data.repository.auth.TokenStorage
import com.example.diyca.data.repository.userdata.UserDataBaseRepository
import com.example.diyca.domain.auth.models.LoginData
import com.example.diyca.domain.auth.models.RegistrationData
import com.example.diyca.domain.auth.models.UserData
import com.example.diyca.domain.auth.recovery.models.ResetPasswordData
import com.example.diyca.domain.auth.recovery.models.VerifyResetCodeData
import com.example.diyca.domain.home.settings.models.ChangeProfileData
import com.example.diyca.domain.home.settings.models.RemoveProfileData
import com.example.diyca.util.ErrorType
import com.example.diyca.util.handleNetworkError
import com.example.diyca.util.safeApiCall

class AuthRepositoryImpl(
    private val authApi: AuthApi,
    private val authRequestMapper: AuthRequestMapper,
    private val authResponseMapper: AuthResponseMapper,
    private val tokenStorage: TokenStorage,
    private val userDataBaseRepository: UserDataBaseRepository
) : AuthRepository {

    override suspend fun login(loginData: LoginData): Resource<UserData> = safeApiCall {
        val response = authApi.login(authRequestMapper.loginRequestToData(loginData))
        if (response.isSuccessful) {
            response.body()?.let { body ->
                val userData = authResponseMapper.loginResponseToDomain(body)
                userDataBaseRepository.insertUserName(userData.nickname)
                userDataBaseRepository.insertUserEmail(userData.email)
                tokenStorage.saveTokens(access = userData.accessToken, refresh = userData.refreshToken)
                Resource.Success(userData)
            } ?: Resource.Error(ErrorType.ServerError)
        } else {
            handleNetworkError(response)
        }
    }

    override suspend fun registration(registrationData: RegistrationData): Resource<Unit> =
        safeApiCall {
            val response = authApi.registration(
                authRequestMapper.registrationRequestToData(registrationData)
            )
            if (response.isSuccessful) Resource.Success(Unit)
            else handleNetworkError(response)
        }

    override suspend fun removeProfile(removeProfileData: RemoveProfileData): Resource<Unit> =
        safeApiCall {
            val response = authApi.removeProfile(
                authRequestMapper.removeProfileRequestToData(removeProfileData)
            )
            if (response.isSuccessful) Resource.Success(Unit)
            else handleNetworkError(response)
        }

    override suspend fun changeProfile(changeProfileData: ChangeProfileData): Resource<Unit> =
        safeApiCall {
            val response = authApi.changeProfile(
                authRequestMapper.changeProfileRequestToData(changeProfileData)
            )
            if (response.isSuccessful) Resource.Success(Unit)
            else handleNetworkError(response)
        }

    override suspend fun passwordReset(email: String): Resource<Unit> = safeApiCall {
        val request = PasswordResetRequest(email = email)
        val response = authApi.passwordReset(request)
        if (response.isSuccessful) Resource.Success(Unit)
        else handleNetworkError(response)
    }

    override suspend fun verifyResetCode(email: String, code: String): Resource<VerifyResetCodeData> =
        safeApiCall {
            val request = VerifyResetCodeRequest(email = email, code = code)
            val response = authApi.verifyResetCode(request)
            if (response.isSuccessful) {
                response.body()?.let { body ->
                    Resource.Success(authResponseMapper.verifyResetCodeToDomain(body))
                } ?: Resource.Error(ErrorType.ServerError)
            } else {
                handleNetworkError(response)
            }
        }

    override suspend fun resetPassword(resetPasswordData: ResetPasswordData): Resource<Unit> = safeApiCall {
        val request = authRequestMapper.resetPasswordRequestToData(resetPasswordData)
        val response = authApi.resetPassword(request)
        if (response.isSuccessful) Resource.Success(Unit)
        else handleNetworkError(response)
    }
}