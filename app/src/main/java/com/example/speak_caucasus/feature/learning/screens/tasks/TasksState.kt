package com.example.speak_caucasus.feature.learning.screens.tasks

import com.example.speak_caucasus.domain.learning.models.task_type.Task

data class TasksState (
    val isLoading: Boolean = false,
    val tasks: List<Task> = emptyList(),
    val tasksListSize: Int = 0,
    val currentTask: Int = 0,
    val selectedWords: List<String> = emptyList(),
    val selectedWord: String = "",
    val selectedLetters: List<String> = emptyList(),
    val answer: Boolean? = null,
){
    val progress: Float
        get() = if (tasks.isEmpty()) 0f
        else currentTask.toFloat() / tasks.size.toFloat()
}