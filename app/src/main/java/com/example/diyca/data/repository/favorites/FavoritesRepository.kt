package com.example.diyca.data.repository.favorites

import com.example.diyca.domain.dictionaries.dictionary.models.DictionaryItem
import com.example.diyca.domain.dictionaries.dictionary.models.DictionaryType
import com.example.diyca.domain.phrasebooks.models.Phrasebook
import kotlinx.coroutines.flow.Flow

interface FavoritesRepository {
    fun getAllFavorites(type: DictionaryType): Flow<List<DictionaryItem>>
    suspend fun updateFavoriteItem(item: DictionaryItem)
}