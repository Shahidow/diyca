package com.example.diyca.domain.favorites.impl

import com.example.diyca.data.repository.favorites.FavoritesRepository
import com.example.diyca.domain.favorites.FavoritesInteractor
import com.example.diyca.domain.dictionaries.dictionary.models.DictionaryItem
import com.example.diyca.domain.dictionaries.dictionary.models.DictionaryType
import kotlinx.coroutines.flow.Flow

class FavoritesInteractorImpl (private val favoritesRepository: FavoritesRepository) : FavoritesInteractor {
    override suspend fun getFavoritesItems(dictionaryType: DictionaryType): Flow<List<DictionaryItem>> {
        return favoritesRepository.getAllFavorites(dictionaryType)
    }

    override suspend fun deleteFavoriteItem(dictionaryItem: DictionaryItem) {
        favoritesRepository.updateFavoriteItem(dictionaryItem)
    }
}