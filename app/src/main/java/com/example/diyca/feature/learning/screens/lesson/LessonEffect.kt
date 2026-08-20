package com.example.diyca.feature.learning.screens.lesson

sealed class LessonEffect {
    data object NavigateBack : LessonEffect()
    data class NavigateToTasks(
        val topicTasksCount: Int,
        val topicId: String,
        val lessonId: String,
        val isContinue: Boolean,
        val lessonTasksCount: Int
    ) : LessonEffect()
}