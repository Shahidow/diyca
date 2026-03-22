package com.example.speak_caucasus.data.db.userdata.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "activity_table",
    indices = [Index(value = ["date"], unique = true)]
)
data class ActivityEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val date: Long,
    val lessonsCompleted: Int,
    val tasksCompleted: Int
)
