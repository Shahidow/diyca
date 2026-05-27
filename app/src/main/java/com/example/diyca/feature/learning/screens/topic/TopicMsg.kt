package com.example.diyca.feature.learning.screens.topic

import com.example.diyca.domain.learning.models.Lesson
import com.example.diyca.ui.navigation.ScreenRoutes
import com.example.diyca.util.ErrorType

sealed class TopicMsg {
    data object BackClicked : TopicMsg()
    data class StartLesson(val lesson: Lesson) : TopicMsg()
    data class Error(val errorType: ErrorType) : TopicMsg()
    data class LoadTopic(val topicRout: ScreenRoutes.TopicRout) : TopicMsg()
    data object LoadData : TopicMsg()
}