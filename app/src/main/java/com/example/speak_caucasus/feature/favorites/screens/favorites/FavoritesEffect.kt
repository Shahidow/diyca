package com.example.speak_caucasus.feature.favorites.screens.favorites

import com.example.speak_caucasus.domain.dictionaries.dictionary.models.DictionaryItem


sealed class FavoritesEffect {
    data class NavigateToItem(val item: DictionaryItem) : FavoritesEffect()
    data class ShowToast(val message: String) : FavoritesEffect()
}