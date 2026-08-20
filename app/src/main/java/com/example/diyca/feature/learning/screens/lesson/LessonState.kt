package com.example.diyca.feature.learning.screens.lesson

data class LessonState(
    val isLoading: Boolean = false,
    val error: Int? = null,
    val showConfirmation: Boolean = false,

    val lessonId: String = "",
    val topicId: String = "",
    val topicTasksCount: Int = 0,
    val number: Int = 0,
    val title: String = "",
    val text: String = "",
    val image: String? = null,
    val audio: String? = null,
    val tasksCount: Int = 0,
    val progress: Float = 0f
)
