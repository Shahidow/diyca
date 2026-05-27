package com.example.diyca.domain.learning.lesson

import com.example.diyca.domain.learning.models.Lesson
import com.example.diyca.util.Resource
import kotlinx.coroutines.flow.Flow

interface LessonInteractor {
    fun getLessons(topicId: String): Flow<Resource<List<Lesson>>>
    fun getLessonProgress(lessonId: String, lessonTaskCount: Int): Flow<Float>
}