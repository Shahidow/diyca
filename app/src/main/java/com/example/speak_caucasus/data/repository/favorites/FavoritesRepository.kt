package com.example.speak_caucasus.data.repository.favorites

import com.example.speak_caucasus.domain.dictionaries.dictionary.models.DictionaryItem
import com.example.speak_caucasus.domain.dictionaries.dictionary.models.DictionaryType
import com.example.speak_caucasus.domain.phrasebooks.models.Phrasebook
import kotlinx.coroutines.flow.Flow

interface FavoritesRepository {
    fun getAllFavorites(type: DictionaryType): Flow<List<DictionaryItem>>
    suspend fun updateFavoriteItem(item: DictionaryItem)
}