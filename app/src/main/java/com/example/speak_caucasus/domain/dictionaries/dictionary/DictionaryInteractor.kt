package com.example.speak_caucasus.domain.dictionaries.dictionary

import com.example.speak_caucasus.domain.dictionaries.dictionary.models.DictionaryItem
import com.example.speak_caucasus.domain.dictionaries.dictionary.models.DictionaryType
import kotlinx.coroutines.flow.Flow

interface DictionaryInteractor {
    suspend fun getDictionary(dictionaryType: DictionaryType): Flow<List<DictionaryItem>>
    suspend fun updateFavoriteItem(dictionaryItem: DictionaryItem)
    suspend fun setDic()
}