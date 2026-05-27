package com.example.diyca.domain.learning.study_plan.impl

import com.example.diyca.data.repository.learning.LearningRepository
import com.example.diyca.data.repository.userdata.UserDataBaseRepository
import com.example.diyca.domain.learning.models.Topic
import com.example.diyca.domain.learning.models.UserProgress
import com.example.diyca.domain.learning.study_plan.StudyPlanInteractor
import com.example.diyca.util.LANGUAGE_ID
import com.example.diyca.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class StudyPlanInteractorImpl(
    private val userDataBaseRepository: UserDataBaseRepository,
    private val learningRepository: LearningRepository
) : StudyPlanInteractor {
    override fun getTopics(): Flow<Resource<List<Topic>>> = flow {
        when (val topicsResource = learningRepository.getTopics(LANGUAGE_ID)) {
            is Resource.Success -> {
                val allTopics = topicsResource.data ?: emptyList()
                emitAll(
                    userDataBaseRepository.getAllProgress().map { progress ->
                        val updatedTopics = calculateTopicsLockState(allTopics, progress)
                        Resource.Success(updatedTopics)
                    }
                )
            }

            is Resource.Error -> {
                emit(Resource.Error(topicsResource.errorType, topicsResource.resultCode))
            }
        }
    }

    private fun calculateTopicsLockState(
        allTopics: List<Topic>,
        progress: List<UserProgress>
    ): List<Topic> {
        if (allTopics.isEmpty()) return emptyList()
        val progressMap = progress.groupBy { it.topicId }
        var lastUnlockedIndex = 0
        for (i in allTopics.indices) {
            val currentTopic = allTopics[i]
            val isEmptyTopic = currentTopic.lessonsCount == 0 || currentTopic.tasksCount == 0
            if (isEmptyTopic) {
                lastUnlockedIndex = if (i < allTopics.lastIndex) {
                    i + 1
                } else {
                    i
                }
                continue
            }
            val completedTasksCount = progressMap[currentTopic.id]?.size ?: 0

            if (completedTasksCount > 0) {
                lastUnlockedIndex =
                    if (completedTasksCount >= currentTopic.tasksCount && i < allTopics.lastIndex) {
                        i + 1
                    } else {
                        i
                    }
            } else {
                break
            }
        }
        return allTopics.mapIndexed { index, topic ->
            val isEmpty = topic.lessonsCount == 0 || topic.tasksCount == 0
            topic.copy(isLocked = if (isEmpty) false else index > lastUnlockedIndex)
        }
    }
}