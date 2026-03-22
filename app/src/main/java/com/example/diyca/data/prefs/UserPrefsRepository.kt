package com.example.diyca.data.prefs

import kotlinx.coroutines.flow.Flow


interface UserPrefsRepository {
    fun getUserNameFlow(): Flow<String>
    suspend fun saveUserName(name: String)
    fun getUserEmailFlow(): Flow<String>
    suspend fun saveUserEmail(email: String)
}