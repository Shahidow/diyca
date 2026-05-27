package com.example.diyca.domain.phrasebooks

import com.example.diyca.domain.dictionaries.dictionary.models.DictionaryItem
import kotlinx.coroutines.flow.Flow

interface PhrasebookItemsInteractor {
    fun getPhrasebookItems(parentId: Int): Flow<List<DictionaryItem.PhrasebookItem>>
    suspend fun updateFavoriteItem(phrasebookItem: DictionaryItem.PhrasebookItem)
}
