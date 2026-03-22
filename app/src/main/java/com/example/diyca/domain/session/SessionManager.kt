package com.example.diyca.domain.session

import kotlinx.coroutines.flow.StateFlow

interface SessionManager {
    val isAuthorized: StateFlow<Boolean>
    fun getAccessToken(): String?
    fun getRefreshToken(): String?
    fun saveTokens(access: String, refresh: String)
    fun logout()
}