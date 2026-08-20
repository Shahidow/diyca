package com.example.diyca.feature.learning.screens.topic

import com.example.diyca.domain.learning.models.Lesson
import com.example.diyca.util.ErrorType

data class TopicState (
    val isLoading: Boolean = false,
    val topicId: String = "",
    val topicTasksCount: Int = 0,
    val title: String = "",
    val audio: String? = null,
    val text: String = "",
    val lessons: List<Lesson> = emptyList(),
    val error: ErrorType? = null
)