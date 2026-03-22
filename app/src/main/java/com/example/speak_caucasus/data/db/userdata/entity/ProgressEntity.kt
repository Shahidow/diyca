package com.example.speak_caucasus.data.db.userdata.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "progress_table", indices = [Index(value = ["taskId"], unique = true)])
data class ProgressEntity(
    @PrimaryKey
    val taskId: String,
    val lessonId: String,
    val themeId: String,
    val completionDate: Long = System.currentTimeMillis()
)