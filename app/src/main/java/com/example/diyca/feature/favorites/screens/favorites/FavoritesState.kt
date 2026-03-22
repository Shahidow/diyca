package com.example.diyca.feature.favorites.screens.favorites

import com.example.diyca.domain.dictionaries.dictionary.models.DictionaryItem
import com.example.diyca.domain.dictionaries.dictionary.models.DictionaryType

data class FavoritesState (
    val isLoading: Boolean = false,
    val selectedSection: DictionaryType = DictionaryType.WORD,
    val words: List<DictionaryItem.Word> = emptyList(),
    val searchWord: String = "",
    val expressions: List<DictionaryItem.Expression> = emptyList(),
    val searchExpression: String = "",
    val proverbs: List<DictionaryItem.Proverb> = emptyList(),
    val searchProverb: String = "",
    val phrasebookItems: List<DictionaryItem.PhrasebookItem> = emptyList(),
    val searchConversationItems: String = "",
)