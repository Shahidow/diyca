package com.example.diyca.data.prefs

import kotlinx.coroutines.flow.Flow


interface UserPrefsRepository {
    fun getUserAvatarFlow(): Flow<String>
    suspend fun saveUserAvatar(avatarKey: String)
    fun getUserNameFlow(): Flow<String>
    suspend fun saveUserName(name: String)
    fun getUserEmailFlow(): Flow<String>
    suspend fun saveUserEmail(email: String)
    suspend fun clearUserData()
    fun getLibVersionsFlow(): Flow<Map<String, String?>>
    suspend fun saveLibVersion(libKey: String, version: String)
}