package com.example.diyca.domain.startup

interface StartupInteractor {
    suspend fun checkVersion()
}