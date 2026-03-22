package com.example.speak_caucasus.feature.learning.screens.study_plan

import com.example.speak_caucasus.domain.learning.models.Lesson

sealed class StudyPlanEffect {
    data class NavigateToLesson(val lesson: Lesson) : StudyPlanEffect()
    object NavigateBack: StudyPlanEffect()
    object ServerError: StudyPlanEffect()
    object InternetError: StudyPlanEffect()
    data class ShowToast(val message: String) : StudyPlanEffect()
}