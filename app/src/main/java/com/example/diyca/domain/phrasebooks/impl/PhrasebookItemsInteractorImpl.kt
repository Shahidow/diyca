package com.example.diyca.domain.phrasebooks.impl

import com.example.diyca.data.repository.dictionaries.DictionaryDataBaseRepository
import com.example.diyca.data.repository.favorites.FavoritesRepository
import com.example.diyca.domain.dictionaries.dictionary.models.DictionaryItem
import com.example.diyca.domain.phrasebooks.PhrasebookItemsInteractor
import kotlinx.coroutines.flow.Flow

class PhrasebookItemsInteractorImpl(
    private val favoritesRepository: FavoritesRepository,
    private val dictionaryDataBaseRepository: DictionaryDataBaseRepository
) : PhrasebookItemsInteractor {
    override fun getPhrasebookItems(parentId: String): Flow<List<DictionaryItem.PhrasebookItem>> =
        dictionaryDataBaseRepository.getPhrasebookItems(parentId)

    override suspend fun updateFavoriteItem(phrasebookItem: DictionaryItem.PhrasebookItem) {
        favoritesRepository.updateFavoriteItem(phrasebookItem)
    }
}