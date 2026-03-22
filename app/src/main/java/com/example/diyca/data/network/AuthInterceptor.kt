package com.example.diyca.data.network

import com.example.diyca.domain.session.SessionManager
import okhttp3.Interceptor

class AuthInterceptor (
    private val sessionManager: SessionManager
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        val accessToken = sessionManager.getAccessToken()

        val request = chain.request().newBuilder()

        if (accessToken != null) {
            request.addHeader(
                "Authorization",
                "Bearer $accessToken"
            )
        }

        return chain.proceed(request.build())
    }
}