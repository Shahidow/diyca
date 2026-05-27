package com.example.diyca.feature.dictionaries.screens.dictionary_item

import com.example.diyca.ui.navigation.ScreenRoutes
import com.example.diyca.domain.dictionaries.dictionary.models.DictionaryItem

sealed class DictionaryItemMsg {
    data class LoadData(val itemData: ScreenRoutes.DictionaryItemRout): DictionaryItemMsg()
    data class DataLoaded(val items: List<DictionaryItem>): DictionaryItemMsg()
    data class UpdateFavorite(val item: DictionaryItem) : DictionaryItemMsg()
    data class ChangeCurrentItem(val item: DictionaryItem) : DictionaryItemMsg()
    data object CloseClicked: DictionaryItemMsg()
}