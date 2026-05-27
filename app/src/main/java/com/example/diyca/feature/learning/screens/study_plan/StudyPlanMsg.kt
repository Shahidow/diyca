package com.example.diyca.feature.learning.screens.study_plan

import com.example.diyca.domain.learning.models.Topic
import com.example.diyca.util.ErrorType

sealed class StudyPlanMsg {
    data class StartTopic(val topic: Topic) : StudyPlanMsg()
    data class Error(val errorType: ErrorType) : StudyPlanMsg()
    data object LoadData : StudyPlanMsg()
}