package com.example.diyca.feature.phrasebooks.screens.phrasebook_items_list

sealed class PhrasebookItemsEffect {
    data object NavigateBack : PhrasebookItemsEffect()
    data class NavigateToItem(val id: String, val parentId: String) : PhrasebookItemsEffect()
}