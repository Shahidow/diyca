package com.example.speak_caucasus.domain.auth.login.impl

import com.example.speak_caucasus.util.Resource
import com.example.speak_caucasus.data.repository.auth.AuthRepository
import com.example.speak_caucasus.domain.auth.login.LoginInteractor
import com.example.speak_caucasus.domain.auth.models.UserData
import com.example.speak_caucasus.domain.auth.models.LoginData
import kotlinx.coroutines.flow.first

class LoginInteractorImpl(
    private val authRepository: AuthRepository
): LoginInteractor {
    override suspend fun login(loginData: LoginData): Resource<UserData> {
        return authRepository.login(loginData).first()
    }
}