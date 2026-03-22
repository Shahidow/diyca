package com.example.diyca.domain.auth.login

import com.example.diyca.util.Resource
import com.example.diyca.domain.auth.models.LoginData
import com.example.diyca.domain.auth.models.UserData

interface LoginInteractor {
    suspend fun login(loginData: LoginData): Resource<UserData>
}