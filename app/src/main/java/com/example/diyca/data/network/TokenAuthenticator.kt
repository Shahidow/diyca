package com.example.diyca.data.network

import com.example.diyca.data.dto.requests.RefreshRequest
import com.example.diyca.domain.session.SessionManager
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Route

class TokenAuthenticator (
    private val sessionManager: SessionManager,
    private val authApi: AuthApi
) : Authenticator {

    override fun authenticate(route: Route?, response: okhttp3.Response): Request? {
        val excludedPaths = listOf("/auth/remove-profile", "/auth/change-profile")

        if (excludedPaths.any { response.request.url.encodedPath.contains(it) }) {
            return null
        }

        if (responseCount(response) >= 2) {
            sessionManager.logout()
            return null
        }

        val refreshToken = sessionManager.getRefreshToken() ?: return null

        val refreshResponse = runBlocking {
            authApi.refreshToken(
                RefreshRequest(refreshToken)
            )
        }

        if (!refreshResponse.isSuccessful) {
            sessionManager.logout()
            return null
        }

        val newTokens = refreshResponse.body()!!.data

        sessionManager.saveTokens(
            access = newTokens.access,
            refresh = newTokens.refresh
        )

        return response.request.newBuilder()
            .header("Authorization", "Bearer ${newTokens.access}")
            .build()
    }

    private fun responseCount(response: okhttp3.Response): Int {
        var count = 1
        var prior = response.priorResponse
        while (prior != null) {
            count++
            prior = prior.priorResponse
        }
        return count
    }
}