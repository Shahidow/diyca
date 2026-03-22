package com.example.diyca.domain.learning.study_plan

import com.example.diyca.domain.learning.models.Lesson

interface StudyPlanInteractor {
    fun getLessons():List<Lesson>
}