package com.example.diyca.domain.home.models

data class DailyActivity(
    val id: Int,
    val date: Long,
    val lessonsCompleted: Int,
    val tasksCompleted: Int
)
