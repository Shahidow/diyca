package com.example.diyca.data.db.dictionary.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "expression_table")
data class ExpressionEntity (
    @PrimaryKey
    val id: Int,
    val original: String,
    val translation: String,
    val isFavorite: Boolean,
    val audio: String?
)