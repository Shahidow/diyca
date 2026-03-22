package com.example.speak_caucasus.domain.auth.login

import com.example.speak_caucasus.util.Resource
import com.example.speak_caucasus.domain.auth.models.LoginData
import com.example.speak_caucasus.domain.auth.models.UserData

interface LoginInteractor {
    suspend fun login(loginData: LoginData): Resource<UserData>
}