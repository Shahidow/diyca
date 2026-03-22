package com.example.speak_caucasus.feature.learning.screens.study_plan

import com.example.speak_caucasus.domain.learning.models.Lesson

data class StudyPlanState (
    val isLoading: Boolean = false,
    val lessonsList: List<Lesson> = emptyList()
)