package com.example.diyca.domain.session.impl

import com.example.diyca.data.repository.auth.TokenStorage
import com.example.diyca.domain.session.SessionManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SessionManagerImpl(
    private val tokenStorage: TokenStorage
): SessionManager {
    private val _isAuthorized =
        MutableStateFlow(tokenStorage.hasTokens())

    override val isAuthorized: StateFlow<Boolean> = _isAuthorized

    override fun saveTokens(access: String, refresh: String) {
        tokenStorage.saveTokens(access, refresh)
        _isAuthorized.value = true
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