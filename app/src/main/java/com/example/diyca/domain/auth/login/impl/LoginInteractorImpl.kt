package com.example.diyca.domain.auth.login.impl

import com.example.diyca.util.Resource
import com.example.diyca.data.repository.auth.AuthRepository
import com.example.diyca.domain.auth.login.LoginInteractor
import com.example.diyca.domain.auth.models.UserData
import com.example.diyca.domain.auth.models.LoginData
import kotlinx.coroutines.flow.first

class LoginInteractorImpl(
    private val authRepository: AuthRepository
): LoginInteractor {
    override suspend fun login(loginData: LoginData): Resource<UserData> {
        return authRepository.login(loginData).first()
    }
}