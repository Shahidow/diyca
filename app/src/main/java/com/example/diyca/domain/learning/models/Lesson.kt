package com.example.diyca.domain.learning.models

data class Lesson(
    val id: Int,
    val title: String,
    val lessonsAmount: Int,
    val newWordsAmount: Int,
    val pic: String,
    val text: String,
    val lessonsList: List<LessonSection>
)

