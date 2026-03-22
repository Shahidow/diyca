package com.example.speak_caucasus.data.repository.auth

interface TokenStorage {
    fun saveTokens(access: String, refresh: String)
    fun getAccessToken(): String?
    fun getRefreshToken(): String?
    fun clearTokens()
    fun hasTokens(): Boolean
}