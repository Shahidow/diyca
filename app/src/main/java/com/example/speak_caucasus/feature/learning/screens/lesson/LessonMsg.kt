package com.example.speak_caucasus.feature.learning.screens.lesson

import com.example.speak_caucasus.domain.learning.models.Lesson

sealed class LessonMsg {
    object LoadData:LessonMsg()
    data class DataLoaded(
        val lesson: Lesson
    ): LessonMsg()
    object ServerError: LessonMsg()
    object InternetError: LessonMsg()
    object BackClicked: LessonMsg()
    data class StartTasks(val id: String): LessonMsg()
}