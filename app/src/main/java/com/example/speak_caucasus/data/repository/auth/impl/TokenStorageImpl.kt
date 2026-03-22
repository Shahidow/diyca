package com.example.speak_caucasus.data.repository.auth.impl

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.example.speak_caucasus.data.repository.auth.TokenStorage

class TokenStorageImpl(context: Context) : TokenStorage {

    private val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private val prefs = EncryptedSharedPreferences.create(
        context,
        "auth_tokens",
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    override fun saveTokens(access: String, refresh: String) {
        prefs.edit()
            .putString("access_token", access)
            .putString("refresh_token", refresh)
            .apply()
    }

    override fun getAccessToken(): String? =
        prefs.getString("access_token", null)

    override fun getRefreshToken(): String? =
        prefs.getString("refresh_token", null)

    override fun clearTokens() {
        prefs.edit().clear().apply()
    }

    override fun hasTokens(): Boolean {
        return getAccessToken() != null && getRefreshToken() != null
    }
}