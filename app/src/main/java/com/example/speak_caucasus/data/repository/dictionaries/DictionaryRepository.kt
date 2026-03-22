package com.example.speak_caucasus.data.repository.dictionaries

import com.example.speak_caucasus.domain.dictionaries.dictionary.models.DictionaryItem
import com.example.speak_caucasus.domain.dictionaries.dictionary.models.DictionaryType
import com.example.speak_caucasus.domain.phrasebooks.models.Phrasebook
import kotlinx.coroutines.flow.Flow

interface DictionaryRepository {
    fun getDictionary(type: DictionaryType): Flow<List<DictionaryItem>>
    suspend fun insertDictionaryItem(dictionaryItem: DictionaryItem)
    fun getPhrasebooks(): Flow<List<Phrasebook>>
    suspend fun insertPhrasebook(phrasebook: Phrasebook)
}