package com.example.speak_caucasus.domain.favorites

import com.example.speak_caucasus.domain.dictionaries.dictionary.models.DictionaryItem
import com.example.speak_caucasus.domain.dictionaries.dictionary.models.DictionaryType
import kotlinx.coroutines.flow.Flow

interface FavoritesInteractor {
    suspend fun getFavoritesItems(dictionaryType: DictionaryType): Flow<List<DictionaryItem>>
    suspend fun deleteFavoriteItem(dictionaryItem: DictionaryItem)
}