package com.example.diyca.domain.learning.lesson.impl

import com.example.diyca.data.repository.learning.LearningRepository
import com.example.diyca.data.repository.userdata.UserDataBaseRepository
import com.example.diyca.domain.learning.models.Lesson
import com.example.diyca.domain.learning.models.UserProgress
import com.example.diyca.domain.learning.lesson.LessonInteractor
import com.example.diyca.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class LessonInteractorImpl(
    private val userDataBaseRepository: UserDataBaseRepository,
    private val learningRepository: LearningRepository
) : LessonInteractor {
    override fun getLessons(topicId: String): Flow<Resource<List<Lesson>>> = flow {
        when (val lessons = learningRepository.getLessons(topicId)) {
            is Resource.Success -> {
                val allLessons = lessons.data ?: emptyList()
                emitAll(
                    userDataBaseRepository.getProgressByTopic(topicId).map { progress ->
                        val updatedLessons = mapLessonsWithProgress(allLessons, progress)
                        Resource.Success(updatedLessons)
                    }
                )
            }

            is Resource.Error -> {
                emit(Resource.Error(lessons.errorType, lessons.resultCode))
            }
        }
    }

    override fun getLessonProgress(lessonId: String, lessonTaskCount: Int): Flow<Float> = flow {
        emitAll(
            userDataBaseRepository.getProgressByLessonCount(lessonId).map {
                it.toFloat() / lessonTaskCount.toFloat()
            }
        )
    }

    private fun mapLessonsWithProgress(
        allLessons: List<Lesson>,
        progress: List<UserProgress>
    ): List<Lesson> {
        val completedTasksByLesson = progress.groupBy { it.lessonId }
        return allLessons.mapIndexed { _, lesson ->
            val completedCount = completedTasksByLesson[lesson.id]?.size ?: 0
            val calculatedProgress = if (lesson.tasksCount > 0) {
                completedCount.toFloat() / lesson.tasksCount.toFloat()
            } else {
                0f
            }
            lesson.copy(progress = calculatedProgress.coerceAtMost(1f))
        }
    }
}