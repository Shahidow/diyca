package com.example.diyca.feature.dictionaries.screens.dictionary

import com.example.diyca.R
import com.example.diyca.domain.dictionaries.dictionary.models.DictionaryType


sealed class DictionaryButtonItems (
    val title: Int,
    val type: DictionaryType
) {
    data object Words: DictionaryButtonItems(R.string.dictionary, DictionaryType.WORD)
    data object Expressions: DictionaryButtonItems(R.string.expressions, DictionaryType.EXPRESSION)
    data object Proverbs: DictionaryButtonItems(R.string.proverbs, DictionaryType.PROVERB)

        companion object{
            val all = listOf(Words, Expressions, Proverbs)
        }
}