package com.example.diyca.domain.home.settings

import com.example.diyca.domain.home.settings.models.ChangeProfileData
import com.example.diyca.util.Resource
import kotlinx.coroutines.flow.Flow

interface SettingsInteractor {
    fun getUserAvatar(): Flow<String>
    suspend fun insertAvatar(avatar: String)
    fun getUserName(): Flow<String>
    suspend fun getUserEmail(): String
    suspend fun insertUserName(userName: String)
    suspend fun clearProgress(): Resource<Unit>
    suspend fun removeProfile(password: String): Resource<Unit>
    suspend fun changeProfile(changeProfileData: ChangeProfileData): Resource<Unit>
    suspend fun logout()
}