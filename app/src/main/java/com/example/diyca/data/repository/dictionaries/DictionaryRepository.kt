package com.example.diyca.data.repository.dictionaries

import com.example.diyca.domain.dictionaries.dictionary.models.DictionaryItem
import com.example.diyca.domain.dictionaries.dictionary.models.DictionaryType
import com.example.diyca.domain.phrasebooks.models.Phrasebook
import kotlinx.coroutines.flow.Flow

interface DictionaryRepository {
    fun getDictionary(type: DictionaryType): Flow<List<DictionaryItem>>
    suspend fun insertDictionaryItem(dictionaryItem: DictionaryItem)
    fun getPhrasebooks(): Flow<List<Phrasebook>>
    suspend fun insertPhrasebook(phrasebook: Phrasebook)
}