package com.example.diyca.data.db.dictionary.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "phrasebook_item_table")
data class PhrasebookItemEntity (
    @PrimaryKey
    val id: Int,
    val parentId: Int,
    val original: String,
    val translation: String,
    val isFavorite: Boolean,
    val audio: String?
)