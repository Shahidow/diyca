package com.example.speak_caucasus.data.prefs

import kotlinx.coroutines.flow.Flow


interface UserPrefsRepository {
    fun getUserNameFlow(): Flow<String>
    suspend fun saveUserName(name: String)
    fun getUserEmailFlow(): Flow<String>
    suspend fun saveUserEmail(email: String)
}