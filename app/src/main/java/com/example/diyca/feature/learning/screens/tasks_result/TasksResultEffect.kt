package com.example.diyca.feature.learning.screens.tasks_result

sealed class TasksResultEffect {
    data object CloseTasksResult : TasksResultEffect()
    data class StartTasks(val isContinue: Boolean) : TasksResultEffect()
}