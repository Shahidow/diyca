package com.example.diyca.domain.learning.tasks_result

import com.example.diyca.domain.learning.models.UserProgress
import com.example.diyca.domain.learning.tasks_result.models.TasksResultModel
import com.example.diyca.domain.rewards.models.Reward
import com.example.diyca.util.Resource

interface TasksResultInteractor {
    suspend fun setProgress(
        progressList: List<UserProgress>,
        lessonId: String,
        topicId: String,
        topicTasksCount: Int,
        lessonTasksCount: Int,
        completedTasks: List<String>
    ): Resource<List<Reward>>

    suspend fun getLessonProgressFloat(lessonId: String, lessonTaskCount: Int): Float
}