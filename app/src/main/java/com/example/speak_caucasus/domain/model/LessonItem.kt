package com.example.speak_caucasus.domain.model

data class LessonItem(
    val id: Int,
    val title: String,
    val text: String,
    val tasksList: List<String> //Поменять
)
