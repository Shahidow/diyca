package com.example.speak_caucasus.feature.favorites.screens.favorites

import androidx.compose.runtime.Composable

sealed class FavoritesButtonItems(
    val title: String,
    //val content: @Composable () -> Unit
) {
    data object Words: FavoritesButtonItems("Слова")
    data object Phrasebook: FavoritesButtonItems("Разговорник")
    data object Phrases: FavoritesButtonItems("Выражения")
    data object Proverbs: FavoritesButtonItems("Пословицы")

    companion object{
        val all = listOf(Words, Phrasebook, Phrases, Proverbs)
    }
}