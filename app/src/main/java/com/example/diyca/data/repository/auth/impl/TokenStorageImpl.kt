package com.example.diyca.data.repository.auth.impl

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.example.diyca.data.repository.auth.TokenStorage
import androidx.core.content.edit

class TokenStorageImpl(context: Context) : TokenStorage {

    private companion object {
        const val PREFS_NAME = "auth_tokens"
        const val KEY_ACCESS_TOKEN = "access_token"
        const val KEY_REFRESH_TOKEN = "refresh_token"
    }

    private val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private val prefs = EncryptedSharedPreferences.create(
        context,
        PREFS_NAME,
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    override fun saveTokens(access: String, refresh: String) {
        prefs.edit {
            putString(KEY_ACCESS_TOKEN, access)
                .putString(KEY_REFRESH_TOKEN, refresh)
        }
    }

    override fun getAccessToken(): String? =
        prefs.getString(KEY_ACCESS_TOKEN, null)

    override fun getRefreshToken(): String? =
        prefs.getString(KEY_REFRESH_TOKEN, null)

    override fun clearTokens() {
        prefs.edit { clear() }
    }

    override fun hasTokens(): Boolean {
        return getAccessToken() != null && getRefreshToken() != null
    }
}