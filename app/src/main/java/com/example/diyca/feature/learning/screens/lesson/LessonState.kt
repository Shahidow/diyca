package com.example.diyca.feature.learning.screens.lesson

import com.example.diyca.domain.learning.models.Lesson

data class LessonState (
    val isLoading: Boolean = false,
    val lesson: Lesson? = null
)