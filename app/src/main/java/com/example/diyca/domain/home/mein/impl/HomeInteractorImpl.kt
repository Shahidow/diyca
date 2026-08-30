package com.example.diyca.domain.home.mein.impl

import com.example.diyca.data.repository.learning.LearningRepository
import com.example.diyca.data.repository.userdata.UserDataBaseRepository
import com.example.diyca.domain.home.mein.CurrentLessonState
import com.example.diyca.domain.home.mein.HomeInteractor
import com.example.diyca.domain.home.models.DailyActivity
import com.example.diyca.domain.rewards.models.Reward
import com.example.diyca.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.combine
import java.time.LocalDate

class HomeInteractorImpl(
    private val userDataBaseRepository: UserDataBaseRepository,
    private val learningRepository: LearningRepository
) : HomeInteractor {

    override fun getUserAvatar(): Flow<String> = userDataBaseRepository.getUserAvatar()
    override fun getUserName(): Flow<String> = userDataBaseRepository.getUserName()

    private val retrySignal =
        MutableSharedFlow<Unit>(replay = 1).apply { tryEmit(Unit) }

    override fun retryGetLesson() {
        retrySignal.tryEmit(Unit)
    }

    override fun getLesson(languageId: String): Flow<CurrentLessonState> {
        return combine(
            userDataBaseRepository.getAllProgress(),
            retrySignal
        ) { allProgress, _ ->
            val topics = when (val topicsResource = learningRepository.getTopics(languageId)) {
                is Resource.Success -> topicsResource.data
                is Resource.Error -> return@combine CurrentLessonState.Error(
                    topicsResource.errorType,
                    topicsResource.resultCode
                )
            } ?: return@combine CurrentLessonState.CourseFinished
            val validTopics = topics.filter { it.lessonsCount > 0 && it.tasksCount > 0 }
            val progressMap = allProgress.groupBy { it.lessonId }
                .mapValues { it.value.size }
            for (topic in validTopics) {
                val lessons = when (val lessonsResource = learningRepository.getLessons(topic.id)) {
                    is Resource.Success -> lessonsResource.data?.sortedBy { it.number }
                    is Resource.Error -> return@combine CurrentLessonState.Error(
                        lessonsResource.errorType,
                        lessonsResource.resultCode
                    )
                } ?: continue
                for (lesson in lessons) {
                    val completedTasksCount = progressMap[lesson.id] ?: 0
                    if (completedTasksCount < lesson.tasksCount) {
                        val currentLesson = lesson.copy(
                            progress = if (lesson.tasksCount > 0)
                                completedTasksCount.toFloat() / lesson.tasksCount
                            else 0f
                        )
                        return@combine CurrentLessonState.Active(
                            lesson = currentLesson,
                            topicId = topic.id
                        )
                    }
                }
            }
            CurrentLessonState.CourseFinished
        }
    }


    override fun getDailyActivity(): Flow<DailyActivity?> {
        val today = LocalDate.now().toString()
        return userDataBaseRepository.getTodayActivity(today)
    }

    override fun getUserRewards(): Flow<List<Reward>> {
        return combine(
            userDataBaseRepository.getAllRewards(),
            userDataBaseRepository.getUserRewards()
        ) { allRewards, openedTitles ->
            val openedSet = openedTitles.toSet()
            allRewards.map { reward -> reward.copy(isOpen = openedSet.contains(reward.title)) }
        }
    }
}