package com.example.speak_caucasus.feature.dictionaries.screens.dictionary_item

import com.example.speak_caucasus.ui.navigation.ScreenRoutes
import com.example.speak_caucasus.domain.dictionaries.dictionary.models.DictionaryItem

sealed class DictionaryItemMsg {
    data class LoadData(val itemData: ScreenRoutes.DictionaryItemRout): DictionaryItemMsg()
    data class DataLoaded(val items: List<DictionaryItem>): DictionaryItemMsg()
    data class UpdateFavorite(val item: DictionaryItem) : DictionaryItemMsg()
    data class ChangeCurrentItem(val item: DictionaryItem) : DictionaryItemMsg()
    object CloseClicked: DictionaryItemMsg()
}