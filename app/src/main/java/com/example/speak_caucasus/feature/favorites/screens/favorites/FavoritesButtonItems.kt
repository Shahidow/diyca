package com.example.speak_caucasus.feature.favorites.screens.favorites

import com.example.speak_caucasus.R
import com.example.speak_caucasus.domain.dictionaries.dictionary.models.DictionaryType

sealed class FavoritesButtonItems(
    val title: Int,
    val type: DictionaryType
) {
    data object Words: FavoritesButtonItems(R.string.words, DictionaryType.WORD)
    data object Phrasebook: FavoritesButtonItems(R.string.phrasebook, DictionaryType.PHRASEBOOK)
    data object Expressions: FavoritesButtonItems(R.string.expressions, DictionaryType.EXPRESSION)
    data object Proverbs: FavoritesButtonItems(R.string.proverbs, DictionaryType.PROVERB)

    companion object{
        val all = listOf(Words, Phrasebook, Expressions, Proverbs)
    }
}