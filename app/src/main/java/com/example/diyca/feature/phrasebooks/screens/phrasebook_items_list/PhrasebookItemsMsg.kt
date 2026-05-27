package com.example.diyca.feature.phrasebooks.screens.phrasebook_items_list

import com.example.diyca.domain.dictionaries.dictionary.models.DictionaryItem

sealed class PhrasebookItemsMsg {
    data object NavigateBack : PhrasebookItemsMsg()
    data class NavigateToItem(val id: Int, val parentId: Int) : PhrasebookItemsMsg()
    data class UpdateFavorite(val item: DictionaryItem.PhrasebookItem) : PhrasebookItemsMsg()
    data class LoadData(val phrasebookId: Int) : PhrasebookItemsMsg()
}