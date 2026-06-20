package com.example.diyca.domain.home.mein

import com.example.diyca.domain.learning.models.Lesson
import com.example.diyca.util.ErrorType

sealed class CurrentLessonState {
    data class Active(
        val lesson: Lesson,
        val topicId: String
    ) : CurrentLessonState()

    data object CourseFinished : CurrentLessonState()

    data class Error(
        val errorType: ErrorType,
        val resultCode: Int? = null
    ) : CurrentLessonState()
}