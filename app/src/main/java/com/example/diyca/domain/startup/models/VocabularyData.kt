package com.example.diyca.domain.startup.models

import com.example.diyca.domain.dictionaries.dictionary.models.DictionaryItem

data class VocabularyData (
    val version: Int,
    val words: List<DictionaryItem.Word>
)