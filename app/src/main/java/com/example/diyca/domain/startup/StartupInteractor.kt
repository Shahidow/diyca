package com.example.diyca.domain.startup

import com.example.diyca.util.LoadingStatus
import kotlinx.coroutines.flow.Flow

interface StartupInteractor {
    fun downloadAndSaveAll(): Flow<LoadingStatus>
}