package com.example.diyca.domain.session.impl

import android.util.Base64
import com.example.diyca.data.repository.auth.TokenStorage
import com.example.diyca.domain.session.SessionManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.json.JSONObject

class SessionManagerImpl(
    private val tokenStorage: TokenStorage,
) : SessionManager {
    private val _isAuthorized = MutableStateFlow<Boolean?>(null)
    override val isAuthorized: StateFlow<Boolean?> = _isAuthorized

    override fun saveTokens(access: String, refresh: String) {
        tokenStorage.saveTokens(access, refresh)
        _isAuthorized.value = true
    }

    override suspend fun validateSession() {
        val refreshToken = tokenStorage.getRefreshToken()
        if (refreshToken == null) {
            _isAuthorized.value = false
            return
        }
        val expTime = getExpiryFromJwt(refreshToken)
        val currentTime = System.currentTimeMillis()
        if (expTime > currentTime) {
            _isAuthorized.value = true
        } else {
            logout()
        }
    }

    private fun getExpiryFromJwt(token: String): Long {
        return try {
            val parts = token.split(".")
            if (parts.size < 2) return 0
            val payload = String(
                Base64.decode(parts[1],
                Base64.URL_SAFE or Base64.NO_WRAP))
            val json = JSONObject(payload)
            json.getLong("exp") * 1000
        } catch (e: Exception) {
            0
        }
    }

    override fun logout() {
        tokenStorage.clearTokens()
        _isAuthorized.value = false
    }

    override fun getAccessToken(): String? =
        tokenStorage.getAccessToken()

    override fun getRefreshToken(): String? =
        tokenStorage.getRefreshToken()
}