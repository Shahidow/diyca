package com.example.speak_caucasus.domain.phrasebooks

import com.example.speak_caucasus.domain.phrasebooks.models.Phrasebook
import kotlinx.coroutines.flow.Flow

interface PhrasebookInteractor {
    suspend fun getPhrasebooks(): Flow<List<Phrasebook>>
}