package com.example.diyca.feature.learning.screens.tasks

sealed class TasksEffect {
    data object CloseTasks : TasksEffect()
    data class NavigateToResult(
        val topicId: String,
        val lessonId: String,
        val completedTasks: List<String>,
        val tasksCount: Int,
        val lessonTasksCount: Int
    ) : TasksEffect()
}