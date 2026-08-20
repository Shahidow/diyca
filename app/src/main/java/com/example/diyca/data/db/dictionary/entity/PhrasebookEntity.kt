package com.example.diyca.data.db.dictionary.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "phrasebook_table")
data class PhrasebookEntity (
    @PrimaryKey
    val id: String,
    val title: String,
    val image: String?,
)