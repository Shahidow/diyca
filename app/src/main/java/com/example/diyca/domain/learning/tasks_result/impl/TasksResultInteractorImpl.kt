package com.example.diyca.domain.learning.tasks_result.impl

import com.example.diyca.data.repository.userdata.UserDataBaseRepository
import com.example.diyca.data.repository.userdata.UserNetworkRepository
import com.example.diyca.domain.learning.models.UserProgress
import com.example.diyca.domain.learning.tasks_result.TasksResultInteractor
import com.example.diyca.domain.rewards.RewardEvaluator
import com.example.diyca.domain.rewards.models.LessonResult
import com.example.diyca.domain.rewards.models.Reward
import com.example.diyca.domain.rewards.models.RewardCalculationInput
import com.example.diyca.util.Resource
import kotlinx.coroutines.flow.first
import java.time.LocalDate

class TasksResultInteractorImpl(
    private val userNetworkRepository: UserNetworkRepository,
    private val userDataBaseRepository: UserDataBaseRepository,
    private val rewardEvaluator: RewardEvaluator
) : TasksResultInteractor {
    override suspend fun setProgress(
        progressList: List<UserProgress>,
        lessonId: String,
        topicId: String,
        topicTasksCount: Int,
        lessonTasksCount: Int,
        completedTasks: List<String>
    ): Resource<List<Reward>> {
        val history = userDataBaseRepository.getAllActivity().first()
        val currentProgress = userDataBaseRepository.getAllProgress().first()
        val earnedRewards = userDataBaseRepository.getUserRewards().first().toSet()
        val allRewardsInDb = userDataBaseRepository.getAllRewards().first()

        val dbTopicTasks = currentProgress.filter { it.topicId == topicId }.map { it.taskId }
        val dbLessonTasks = currentProgress.filter { it.lessonId == lessonId }.map { it.taskId }
        val allTopicTasks = (dbTopicTasks + completedTasks).distinct()
        val allLessonTasks = (dbLessonTasks + completedTasks).distinct()

        val isTopicCompleted = allTopicTasks.size == topicTasksCount
        val isLessonCompleted = allLessonTasks.size == lessonTasksCount
        val todayDate = LocalDate.now().toString()
        val lessonResult = LessonResult(
            lessonId = lessonId,
            topicId = topicId,
            isTopicCompleted = isTopicCompleted,
            isLessonCompleted = isLessonCompleted,
            lessonTasksCount = lessonTasksCount,
            completedTasks = completedTasks,
            date = todayDate
        )

        val newRewardIds = rewardEvaluator.evaluate(
            RewardCalculationInput(
                currentActivity = history,
                currentProgress = currentProgress,
                alreadyEarnedIds = earnedRewards,
                currentLessonResult = lessonResult,
                allAvailableRewards = allRewardsInDb
            )
        )

        return when (val resource = userNetworkRepository.setProgress(progressList, newRewardIds)) {
            is Resource.Error -> Resource.Error(resource.errorType, resource.resultCode)
            is Resource.Success -> {
                progressList.forEach { userDataBaseRepository.insertUserProgress(it) }
                resource.data?.activity?.let { userDataBaseRepository.insertActivity(it) }
                if (newRewardIds.isNotEmpty()) {
                    userDataBaseRepository.insertUserRewards(newRewardIds)
                }
                val rewardModels = allRewardsInDb.filter { it.title in newRewardIds }
                Resource.Success(rewardModels)
            }
        }
    }

    override suspend fun getLessonProgressFloat(lessonId: String, lessonTaskCount: Int): Float {
        val lessonProgress = userDataBaseRepository.getProgressByLessonCount(lessonId).first()
        return lessonProgress.toFloat() / lessonTaskCount.toFloat()
    }
}