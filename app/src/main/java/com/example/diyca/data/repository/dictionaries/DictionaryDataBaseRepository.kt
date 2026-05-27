package com.example.diyca.data.repository.dictionaries

import com.example.diyca.domain.dictionaries.dictionary.models.DictionaryItem
import com.example.diyca.domain.dictionaries.dictionary.models.DictionaryType
import com.example.diyca.domain.phrasebooks.models.Phrasebook
import kotlinx.coroutines.flow.Flow

interface DictionaryDataBaseRepository {
    fun getDictionary(type: DictionaryType): Flow<List<DictionaryItem>>
    fun getPhrasebookItems(parentId: Int): Flow<List<DictionaryItem.PhrasebookItem>>
    suspend fun insertDictionaryItem(dictionaryItem: DictionaryItem)
    fun getPhrasebooks(): Flow<List<Phrasebook>>
    suspend fun insertPhrasebook(phrasebook: Phrasebook)
}