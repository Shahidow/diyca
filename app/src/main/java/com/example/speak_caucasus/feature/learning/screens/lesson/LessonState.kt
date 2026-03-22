package com.example.speak_caucasus.feature.learning.screens.lesson

import com.example.speak_caucasus.domain.learning.models.Lesson

data class LessonState (
    val isLoading: Boolean = false,
    val lesson: Lesson? = null
)