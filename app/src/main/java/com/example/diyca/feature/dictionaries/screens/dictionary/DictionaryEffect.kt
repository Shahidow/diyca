package com.example.diyca.feature.dictionaries.screens.dictionary

import com.example.diyca.domain.dictionaries.dictionary.models.DictionaryItem

sealed class DictionaryEffect {
    data class NavigateToItem(val item: DictionaryItem) : DictionaryEffect()
}