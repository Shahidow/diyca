package com.example.diyca.feature.dictionaries.screens.dictionary

import com.example.diyca.domain.dictionaries.dictionary.models.DictionaryItem
import com.example.diyca.domain.dictionaries.dictionary.models.DictionaryType

data class DictionaryState(
    val selectedSection: DictionaryType = DictionaryType.WORD,

    val words: List<DictionaryItem.Word> = emptyList(),
    val expressions: List<DictionaryItem.Expression> = emptyList(),
    val proverbs: List<DictionaryItem.Proverb> = emptyList(),

    val filteredWords: List<DictionaryItem.Word> = emptyList(),
    val filteredExpressions: List<DictionaryItem.Expression> = emptyList(),
    val filteredProverbs: List<DictionaryItem.Proverb> = emptyList(),
    val filteredPhrasebookItems: List<DictionaryItem.PhrasebookItem> = emptyList(),

    val searchWord: String = "",
    val searchExpression: String = "",
    val searchProverb: String = "",
    val currentSearchText: String = "",
)