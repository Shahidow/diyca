package com.example.diyca.domain.learning.lesson

import com.example.diyca.domain.learning.models.Lesson

interface LessonInteractor {
    fun getLesson(): Lesson
}