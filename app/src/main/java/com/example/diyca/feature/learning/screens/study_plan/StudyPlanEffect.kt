package com.example.diyca.feature.learning.screens.study_plan

import com.example.diyca.domain.learning.models.Topic

sealed class StudyPlanEffect {
    data class NavigateToLesson(val topic: Topic) : StudyPlanEffect()
}