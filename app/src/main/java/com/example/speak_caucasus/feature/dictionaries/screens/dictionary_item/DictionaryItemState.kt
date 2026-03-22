package com.example.speak_caucasus.feature.dictionaries.screens.dictionary_item

import com.example.speak_caucasus.domain.dictionaries.dictionary.models.DictionaryItem

data class DictionaryItemState(
    val isLoading: Boolean = false,
    val items: List<DictionaryItem> = emptyList(),
    val currentItem: DictionaryItem? = null
)
