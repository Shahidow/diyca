package com.example.diyca.domain.phrasebooks

import com.example.diyca.domain.phrasebooks.models.Phrasebook
import kotlinx.coroutines.flow.Flow

interface PhrasebookInteractor {
    suspend fun getPhrasebooks(): Flow<List<Phrasebook>>
}