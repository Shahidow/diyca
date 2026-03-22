package com.example.diyca.feature.learning.screens.tasks

sealed class TasksEffect {
    data class NavigateToResult(val data: String): TasksEffect()
}