package com.example.diyca.feature.learning.screens.study_plan

import com.example.diyca.domain.learning.models.Lesson

data class StudyPlanState (
    val isLoading: Boolean = false,
    val lessonsList: List<Lesson> = emptyList()
)