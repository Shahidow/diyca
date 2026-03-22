package com.example.speak_caucasus.data.db.dictionary.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "word_table")
data class WordEntity(
    @PrimaryKey
    val id: Int,
    val original: String,
    val translation: String,
    val isFavorite: Boolean,
    val audio: String?
)
