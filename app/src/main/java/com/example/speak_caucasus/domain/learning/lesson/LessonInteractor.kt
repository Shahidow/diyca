package com.example.speak_caucasus.domain.learning.lesson

import com.example.speak_caucasus.domain.learning.models.Lesson

interface LessonInteractor {
    fun getLesson(): Lesson
}