package com.example.diyca.domain.learning.models

data class LessonSection(
    val id: Int,
    val section: String,
    val title: String,
    val text: String,
    val tasksList: List<String> //Поменять
)
