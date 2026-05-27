package com.example.diyca.data.db.userdata.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "activity_table")
data class ActivityEntity(
    @PrimaryKey
    val date: String,
    val lessonsCompleted: Int,
    val tasksCompleted: Int
)
