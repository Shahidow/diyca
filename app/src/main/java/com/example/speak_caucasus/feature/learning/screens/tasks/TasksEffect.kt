package com.example.speak_caucasus.feature.learning.screens.tasks

sealed class TasksEffect {
    data class NavigateToResult(val data: String): TasksEffect()
}