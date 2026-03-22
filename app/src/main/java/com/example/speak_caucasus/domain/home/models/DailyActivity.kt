package com.example.speak_caucasus.domain.home.models

data class DailyActivity(
    val id: Int,
    val date: Long,
    val lessonsCompleted: Int,
    val tasksCompleted: Int
)
