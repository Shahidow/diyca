package com.example.diyca.feature.favorites.screens.favorites

import com.example.diyca.domain.dictionaries.dictionary.models.DictionaryItem


sealed class FavoritesEffect {
    data class NavigateToItem(val item: DictionaryItem) : FavoritesEffect()
    data class ShowToast(val message: String) : FavoritesEffect()
}