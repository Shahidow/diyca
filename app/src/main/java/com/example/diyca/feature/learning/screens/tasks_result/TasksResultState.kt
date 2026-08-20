package com.example.diyca.feature.learning.screens.tasks_result

import com.example.diyca.domain.home.models.Reward
import com.example.diyca.util.ErrorType

data class TasksResultState(
    val topicId: String = "",
    val topicTasksCount: Int = 0,
    val lessonId: String = "",
    val title: Int? = null,
    val completedTasks: List<String> = emptyList(),
    val tasksCount: Int = 0,
    val progress: Float = 0f,
    val lessonTasksCount: Int = 0,
    val lessonProgress: Float = 1f,

    val isLoading: Boolean = false,
    val error: ErrorType? = null,
    val reward: Reward? = null,
    val showConfirmation: Boolean = false,
)