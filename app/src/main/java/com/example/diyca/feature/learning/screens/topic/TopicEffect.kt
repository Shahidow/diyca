package com.example.diyca.feature.learning.screens.topic

import com.example.diyca.domain.learning.models.Lesson


sealed class TopicEffect {
    data object NavigateBack: TopicEffect()
    data class NavigateToLesson(val lesson: Lesson): TopicEffect()
}