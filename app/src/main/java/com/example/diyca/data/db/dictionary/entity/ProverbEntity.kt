package com.example.diyca.data.db.dictionary.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "proverb_table")
data class ProverbEntity (
    @PrimaryKey
    val id: String,
    val original: String,
    val translation: String,
    val isFavorite: Boolean,
    val audio: String?
)