package com.example.diyca.data.repository.auth.impl

import com.example.diyca.data.dto.requests.PasswordResetRequest
import com.example.diyca.data.dto.requests.VerifyResetCodeRequest
import com.example.diyca.data.mappers.AuthRequestMapper
import com.example.diyca.data.mappers.AuthResponseMapper
import com.example.diyca.data.network.UserApi
import com.example.diyca.util.Resource
import com.example.diyca.data.repository.auth.AuthRepository
import com.example.diyca.data.repository.auth.TokenStorage
import com.example.diyca.data.repository.userdata.UserDataRepository
import com.example.diyca.domain.auth.models.LoginData
import com.example.diyca.domain.auth.models.RegistrationData
import com.example.diyca.domain.auth.models.UserData
import com.example.diyca.domain.auth.recovery.models.ResetPasswordData
import com.example.diyca.domain.auth.recovery.models.VerifyResetCodeData
import com.example.diyca.domain.home.settings.models.ChangeProfileData
import com.example.diyca.domain.home.settings.models.RemoveProfileData
import com.example.diyca.util.ErrorType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import java.io.IOException

class AuthRepositoryImpl(
    private val userApi: UserApi,
    private val authRequestMapper: AuthRequestMapper,
    private val authResponseMapper: AuthResponseMapper,
    private val tokenStorage: TokenStorage,
    private val userDataRepository: UserDataRepository
) : AuthRepository {

    override fun login(loginData: LoginData): Flow<Resource<UserData>> = safeApiCall {
        val response = userApi.login(authRequestMapper.loginRequestToData(loginData))

        if (response.isSuccessful) {
            response.body()?.let { body ->
                val userData = authResponseMapper.loginResponseToDomain(body)
                userDataRepository.insertUserName(userData.nickname)
                userDataRepository.insertUserEmail(userData.email)
                tokenStorage.saveTokens(access = userData.accessToken, refresh = userData.refreshToken)
                Resource.Success(authResponseMapper.loginResponseToDomain(body))
            } ?: Resource.Error(ErrorType.ServerError)
        } else {
            handleError(response)
        }
    }

    override fun registration(registrationData: RegistrationData): Flow<Resource<Unit>> =
        safeApiCall {
            val response = userApi.registration(
                authRequestMapper.registrationRequestToData(registrationData)
            )
            if (response.isSuccessful) Resource.Success(Unit)
            else handleError(response)
        }

    override fun removeProfile(removeProfileData: RemoveProfileData): Flow<Resource<Unit>> =
        safeApiCall {
            val response = userApi.removeProfile(
                authRequestMapper.removeProfileRequestToData(removeProfileData)
            )
            if (response.isSuccessful) Resource.Success(Unit)
            else handleError(response)
        }

    override fun changeProfile(changeProfileData: ChangeProfileData): Flow<Resource<Unit>> =
        safeApiCall {
            val response = userApi.changeProfile(
                authRequestMapper.changeProfileRequestToData(changeProfileData)
            )
            if (response.isSuccessful) Resource.Success(Unit)
            else handleError(response)
        }

    override fun passwordReset(email: String): Flow<Resource<Unit>> = safeApiCall {
        val request = PasswordResetRequest(email = email)
        val response = userApi.passwordReset(request)
        if (response.isSuccessful) Resource.Success(Unit)
        else handleError(response)
    }

    override fun verifyResetCode(email: String, code: String): Flow<Resource<VerifyResetCodeData>> =
        safeApiCall {
            val request = VerifyResetCodeRequest(email = email, code = code)
            val response = userApi.verifyResetCode(request)
            if (response.isSuccessful) {
                response.body()?.let { body ->
                    Resource.Success(authResponseMapper.verifyResetCodeToDomain(body))
                } ?: Resource.Error(ErrorType.ServerError)
            } else {
                handleError(response)
            }
        }

    override fun resetPassword(resetPasswordData: ResetPasswordData): Flow<Resource<Unit>> = safeApiCall {
        val request = authRequestMapper.resetPasswordRequestToData(resetPasswordData)
        val response = userApi.resetPassword(request)
        if (response.isSuccessful) Resource.Success(Unit)
        else handleError(response)
    }

    private fun <T> safeApiCall(call: suspend () -> Resource<T>): Flow<Resource<T>> = flow {
        emit(call())
    }.catch { e ->
        when (e) {
            is IOException -> emit(Resource.Error(ErrorType.NetworkError))
            else -> emit(Resource.Error(ErrorType.Unknown))
        }
    }

    private fun <T> handleError(response: retrofit2.Response<*>): Resource<T> {
        return when (val code = response.code()) {
            401 -> Resource.Error(ErrorType.Unauthorized, code)
            403 -> Resource.Error(ErrorType.Forbidden, code)
            404 -> Resource.Error(ErrorType.NotFound, code)
            409 -> Resource.Error(ErrorType.EmailAlreadyExists, code)
            in 500..599 -> Resource.Error(ErrorType.ServerError, code)
            else -> Resource.Error(ErrorType.Unknown, code)
        }
    }
}