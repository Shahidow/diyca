package com.example.diyca.domain.phrasebooks.impl

import com.example.diyca.data.repository.dictionaries.DictionaryDataBaseRepository
import com.example.diyca.domain.phrasebooks.PhrasebookInteractor
import com.example.diyca.domain.phrasebooks.models.Phrasebook
import kotlinx.coroutines.flow.Flow

class PhrasebookInteractorImpl(
    private val dictionaryDataBaseRepository: DictionaryDataBaseRepository
) : PhrasebookInteractor {
    override suspend fun getPhrasebooks(): Flow<List<Phrasebook>> {
        return dictionaryDataBaseRepository.getPhrasebooks()
    }
}