package com.example.diyca.domain.learning.models

data class Lesson(
    val id: String,
    val number: Int,
    val title: String,
    val text: String,
    val image: String?,
    val audio: String?,
    val tasksCount: Int,
    val progress: Float = 0f
)