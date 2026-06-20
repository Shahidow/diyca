package com.example.diyca.domain.dictionaries.dictionary.impl

import com.example.diyca.data.repository.dictionaries.DictionaryDataBaseRepository
import com.example.diyca.data.repository.favorites.FavoritesRepository
import com.example.diyca.domain.dictionaries.dictionary.DictionaryInteractor
import com.example.diyca.domain.dictionaries.dictionary.models.DictionaryItem
import com.example.diyca.domain.dictionaries.dictionary.models.DictionaryType
import kotlinx.coroutines.flow.Flow

class DictionaryInteractorImpl(
    private val favoritesRepository: FavoritesRepository,
    private val dictionaryDataBaseRepository: DictionaryDataBaseRepository
) :
    DictionaryInteractor {
    override fun getDictionary(dictionaryType: DictionaryType): Flow<List<DictionaryItem>> =
        dictionaryDataBaseRepository.getDictionary(dictionaryType)

    override suspend fun updateFavoriteItem(dictionaryItem: DictionaryItem) {
        favoritesRepository.updateFavoriteItem(dictionaryItem)
    }
}

