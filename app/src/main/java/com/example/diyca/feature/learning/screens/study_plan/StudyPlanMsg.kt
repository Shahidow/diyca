package com.example.diyca.feature.learning.screens.study_plan

import com.example.diyca.domain.learning.models.Lesson

sealed class StudyPlanMsg {
    object LoadData: StudyPlanMsg()
    data class DataLoaded(
        val lessonsList: List<Lesson>,
    ): StudyPlanMsg()
    object ServerError: StudyPlanMsg()
    object InternetError: StudyPlanMsg()
    data class StartLesson(val lesson: Lesson): StudyPlanMsg()
    object BackClicked: StudyPlanMsg()
}