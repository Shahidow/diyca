package com.example.diyca.domain.favorites

import com.example.diyca.domain.dictionaries.dictionary.models.DictionaryItem
import com.example.diyca.domain.dictionaries.dictionary.models.DictionaryType
import kotlinx.coroutines.flow.Flow

interface FavoritesInteractor {
    suspend fun getFavoritesItems(dictionaryType: DictionaryType): Flow<List<DictionaryItem>>
    suspend fun deleteFavoriteItem(dictionaryItem: DictionaryItem)
}