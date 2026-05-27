package com.example.diyca.feature.phrasebooks.screens.phrasebook_items_list

import com.example.diyca.domain.dictionaries.dictionary.models.DictionaryItem

data class PhrasebookItemsState (
    val phrasebookItems: List<DictionaryItem.PhrasebookItem> = emptyList()
)