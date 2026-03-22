package com.example.speak_caucasus.domain.dictionaries.dictionary.models

sealed class DictionaryItem {
    abstract val id: Int
    abstract val original: String
    abstract val translation: String
    abstract val isFavorite: Boolean
    abstract val audio: String?

    data class PhrasebookItem(
        val parentId: Int,
        override val id: Int,
        override val original: String,
        override val translation: String,
        override val isFavorite: Boolean,
        override val audio: String?
    ) : DictionaryItem()

    data class Expression(
        override val id: Int,
        override val original: String,
        override val translation: String,
        override val isFavorite: Boolean,
        override val audio: String?
    ) : DictionaryItem()

    data class Proverb(
        override val id: Int,
        override val original: String,
        override val translation: String,
        override val isFavorite: Boolean,
        override val audio: String?
    ) : DictionaryItem()

    data class Word(
        override val id: Int,
        override val original: String,
        override val translation: String,
        override val isFavorite: Boolean,
        override val audio: String?
    ) : DictionaryItem()
}