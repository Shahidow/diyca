package com.example.diyca.feature.learning.screens.tasks

import com.example.diyca.domain.learning.models.task_type.BuildSentenceTask
import com.example.diyca.domain.learning.models.task_type.BuildWordTask
import com.example.diyca.domain.learning.models.task_type.MultipleChoiceTask
import com.example.diyca.domain.learning.models.task_type.SingleChoiceTask
import com.example.diyca.domain.learning.models.task_type.Task
import com.example.diyca.util.ErrorType

data class TasksState (
    val isLoading: Boolean = false,
    val tasks: List<Task> = emptyList(),
    val tasksListSize: Int = 0,
    val currentTask: Int = 0,
    val lessonTasksCount: Int = 0,

    val selectedWords: List<String> = emptyList(),
    val selectedSingleWord: String = "",
    val selectedMultipleWords: List<String> = emptyList(),
    val selectedLetters: List<String> = emptyList(),
    val answer: Boolean? = null,

    val completedTasks: List<String> = emptyList(),
    val topicId: String = "",
    val lessonId: String = "",

    val error: ErrorType? = null,
    val showCloseConfirmation: Boolean = false,
){
    val isAnswerNotEmpty: Boolean
        get() {
            val task = tasks.getOrNull(currentTask) ?: return false
            return when (task) {
                is BuildSentenceTask -> selectedWords.isNotEmpty()
                is BuildWordTask -> selectedLetters.isNotEmpty()
                is SingleChoiceTask -> selectedSingleWord.isNotEmpty()
                is MultipleChoiceTask -> selectedMultipleWords.isNotEmpty()
            }
        }

    val progress: Float
        get() = if (tasks.isEmpty()) 0f
        else currentTask.toFloat() / tasks.size.toFloat()
}