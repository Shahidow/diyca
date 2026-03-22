package com.example.diyca.feature.dictionaries.screens.dictionary_item

import com.example.diyca.domain.dictionaries.dictionary.models.DictionaryItem

data class DictionaryItemState(
    val isLoading: Boolean = false,
    val items: List<DictionaryItem> = emptyList(),
    val currentItem: DictionaryItem? = null
)
