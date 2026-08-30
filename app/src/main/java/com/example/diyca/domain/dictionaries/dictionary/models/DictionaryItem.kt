package com.example.diyca.domain.dictionaries.dictionary.models

sealed class DictionaryItem {
    abstract val id: String
    abstract val original: String
    abstract val translation: String
    abstract val isFavorite: Boolean
    abstract val audio: String?

    private fun normalize(text: String): String {
        return text.lowercase()
            .replace("1", "Ӏ")
            .replace("i", "Ӏ")
            .replace("l", "Ӏ")
    }

    fun matches(query: String): Boolean {
        if (query.isBlank()) return true
        val normalizedQuery = normalize(query)
        return original.contains(normalizedQuery, ignoreCase = true) ||
                translation.contains(normalizedQuery, ignoreCase = true)
    }

    data class PhrasebookItem(
        val parentId: String,
        val usingExample: String,
        override val id: String,
        override val original: String,
        override val translation: String,
        override val isFavorite: Boolean,
        override val audio: String?
    ) : DictionaryItem()

    data class Expression(
        override val id: String,
        override val original: String,
        override val translation: String,
        override val isFavorite: Boolean,
        override val audio: String?
    ) : DictionaryItem()

    data class Proverb(
        override val id: String,
        override val original: String,
        override val translation: String,
        override val isFavorite: Boolean,
        override val audio: String?
    ) : DictionaryItem()

    data class Word(
        override val id: String,
        override val original: String,
        override val translation: String,
        override val isFavorite: Boolean,
        override val audio: String?
    ) : DictionaryItem()
}