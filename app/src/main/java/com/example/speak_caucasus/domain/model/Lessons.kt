package com.example.speak_caucasus.domain.model

data class Lessons(
    val id: Int,
    val title: String,
    val lessonsAmount: Int,
    val newWordsAmount: Int,
    val pic: Int,
    val text: String,
    val lessonsList: List<LessonItem>
)

