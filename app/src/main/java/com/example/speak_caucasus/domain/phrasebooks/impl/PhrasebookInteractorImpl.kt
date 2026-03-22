package com.example.speak_caucasus.domain.phrasebooks.impl

import com.example.speak_caucasus.data.repository.dictionaries.DictionaryRepository
import com.example.speak_caucasus.domain.phrasebooks.PhrasebookInteractor
import com.example.speak_caucasus.domain.phrasebooks.models.Phrasebook
import kotlinx.coroutines.flow.Flow

class PhrasebookInteractorImpl(
    private val dictionaryRepository: DictionaryRepository
) : PhrasebookInteractor {
    override suspend fun getPhrasebooks(): Flow<List<Phrasebook>> {
        return dictionaryRepository.getPhrasebooks()
    }
}