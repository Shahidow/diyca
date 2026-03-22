package com.example.diyca.domain.home.settings

import com.example.diyca.domain.home.settings.models.ChangeProfileData
import com.example.diyca.domain.home.settings.models.RemoveProfileData
import com.example.diyca.domain.home.settings.models.UserSettings
import com.example.diyca.util.Resource
import kotlinx.coroutines.flow.Flow

interface SettingsInteractor {
    fun getUserSettings(): UserSettings
    fun getUserName(): Flow<String>
    suspend fun getUserEmail(): String
    suspend fun insertUserName(userName: String)
    suspend fun removeProfile(removeProfileData: RemoveProfileData): Resource<Unit>
    suspend fun changeProfile(changeProfileData: ChangeProfileData): Resource<Unit>
}