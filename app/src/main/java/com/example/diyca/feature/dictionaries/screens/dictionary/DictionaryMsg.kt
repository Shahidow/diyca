package com.example.diyca.feature.dictionaries.screens.dictionary

import com.example.diyca.domain.dictionaries.dictionary.models.DictionaryItem
import com.example.diyca.domain.dictionaries.dictionary.models.DictionaryType

sealed class DictionaryMsg {
    data class LoadData(val section: DictionaryType) : DictionaryMsg()
    data class DataLoaded(val type: DictionaryType, val items: List<DictionaryItem>) :
        DictionaryMsg()

    data class UpdateFavorite(val item: DictionaryItem) : DictionaryMsg()
    data class SearchText(val text: String, val section: DictionaryType) : DictionaryMsg()
    data class InternalNavigate(val section: DictionaryType) : DictionaryMsg()
    data class NavigateToItem(val item: DictionaryItem) : DictionaryMsg()
}