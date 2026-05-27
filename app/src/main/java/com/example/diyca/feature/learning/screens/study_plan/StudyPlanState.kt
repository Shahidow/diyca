package com.example.diyca.feature.learning.screens.study_plan

import com.example.diyca.domain.learning.models.Topic
import com.example.diyca.util.ErrorType

data class StudyPlanState (
    val isLoading: Boolean = false,
    val lessonsList: List<Topic> = emptyList(),
    val error: ErrorType? = null
)