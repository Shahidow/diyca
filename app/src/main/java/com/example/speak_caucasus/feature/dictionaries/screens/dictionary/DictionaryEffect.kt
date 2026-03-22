package com.example.speak_caucasus.feature.dictionaries.screens.dictionary

import com.example.speak_caucasus.domain.dictionaries.dictionary.models.DictionaryItem

sealed class DictionaryEffect {
    data class NavigateToItem(val item: DictionaryItem) : DictionaryEffect()
    data class ShowToast(val message: String) : DictionaryEffect()
}