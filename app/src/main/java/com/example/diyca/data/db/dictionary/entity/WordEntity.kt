package com.example.diyca.data.db.dictionary.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "word_table")
data class WordEntity(
    @PrimaryKey
    val id: String,
    val original: String,
    val translation: String,
    val isFavorite: Boolean,
    val audio: String?
)
