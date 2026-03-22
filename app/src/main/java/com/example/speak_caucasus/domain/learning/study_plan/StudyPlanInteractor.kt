package com.example.speak_caucasus.domain.learning.study_plan

import com.example.speak_caucasus.domain.learning.models.Lesson

interface StudyPlanInteractor {
    fun getLessons():List<Lesson>
}