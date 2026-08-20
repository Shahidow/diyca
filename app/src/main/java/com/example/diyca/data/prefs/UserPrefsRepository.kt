package com.example.diyca.data.prefs

import com.example.diyca.domain.startup.LibraryKeys
import kotlinx.coroutines.flow.Flow


interface UserPrefsRepository {
    fun getUserAvatarFlow(): Flow<String>
    suspend fun saveUserAvatar(avatarKey: String)
    fun getUserNameFlow(): Flow<String>
    suspend fun saveUserName(name: String)
    fun getUserEmailFlow(): Flow<String>
    suspend fun saveUserEmail(email: String)
    suspend fun clearUserData()
    fun getLibVersions(libKey: LibraryKeys): Flow<Int?>
    suspend fun saveLibVersion(libKey: LibraryKeys, version: Int)
    fun getUserRewards(): Flow<List<String>>
    suspend fun saveUserRewards(rewardTitles: List<String>)
}