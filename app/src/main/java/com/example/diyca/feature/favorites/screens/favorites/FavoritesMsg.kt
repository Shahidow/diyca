package com.example.diyca.feature.favorites.screens.favorites

import com.example.diyca.domain.dictionaries.dictionary.models.DictionaryItem
import com.example.diyca.domain.dictionaries.dictionary.models.DictionaryType


sealed class FavoritesMsg {
    data class LoadData(val section: DictionaryType) : FavoritesMsg()
    data class DataLoaded(val type: DictionaryType, val items: List<DictionaryItem>) :
        FavoritesMsg()

    data class DeleteFromFavorites(val item: DictionaryItem) : FavoritesMsg()
    data class SearchText(val text: String, val section: DictionaryType) : FavoritesMsg()
    data class InternalNavigate(val section: DictionaryType) : FavoritesMsg()
    data class NavigateToItem(val item: DictionaryItem): FavoritesMsg()
}