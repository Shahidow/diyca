package com.example.speak_caucasus.feature.dictionaries.screens.dictionary

import com.example.speak_caucasus.domain.dictionaries.dictionary.models.DictionaryItem
import com.example.speak_caucasus.domain.dictionaries.dictionary.models.DictionaryType

data class DictionaryState (
    val isLoading: Boolean = false,
    val selectedSection: DictionaryType = DictionaryType.WORD,
    val words: List<DictionaryItem.Word> = emptyList(),
    val searchWord: String = "",
    val expressions: List<DictionaryItem.Expression> = emptyList(),
    val searchExpression: String = "",
    val proverbs: List<DictionaryItem.Proverb> = emptyList(),
    val searchProverb: String = "",
)