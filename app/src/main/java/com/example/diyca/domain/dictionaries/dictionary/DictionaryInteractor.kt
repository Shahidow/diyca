package com.example.diyca.domain.dictionaries.dictionary

import com.example.diyca.domain.dictionaries.dictionary.models.DictionaryItem
import com.example.diyca.domain.dictionaries.dictionary.models.DictionaryType
import kotlinx.coroutines.flow.Flow

interface DictionaryInteractor {
    fun getDictionary(dictionaryType: DictionaryType): Flow<List<DictionaryItem>>
    suspend fun updateFavoriteItem(dictionaryItem: DictionaryItem)
    suspend fun setDic()
}