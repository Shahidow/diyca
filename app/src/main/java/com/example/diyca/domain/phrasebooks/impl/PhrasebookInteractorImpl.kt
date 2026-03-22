package com.example.diyca.domain.phrasebooks.impl

import com.example.diyca.data.repository.dictionaries.DictionaryRepository
import com.example.diyca.domain.phrasebooks.PhrasebookInteractor
import com.example.diyca.domain.phrasebooks.models.Phrasebook
import kotlinx.coroutines.flow.Flow

class PhrasebookInteractorImpl(
    private val dictionaryRepository: DictionaryRepository
) : PhrasebookInteractor {
    override suspend fun getPhrasebooks(): Flow<List<Phrasebook>> {
        return dictionaryRepository.getPhrasebooks()
    }
}