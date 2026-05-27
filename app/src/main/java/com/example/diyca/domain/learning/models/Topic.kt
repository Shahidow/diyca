package com.example.diyca.domain.learning.models

data class Topic(
    val id: String,
    val header: String,
    val audio: String?,
    val image: String?,
    val text: String,
    val lessonsCount: Int,
    val tasksCount: Int,
    val isLocked: Boolean = false,
)

