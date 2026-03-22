package com.example.speak_caucasus.feature.learning.screens.tasks

import com.example.speak_caucasus.domain.learning.models.task_type.Task


sealed class TasksMsg {
    object LoadData : TasksMsg()
    data class DataLoaded(val items: List<Task>) : TasksMsg()
    data class Error(val message: String) : TasksMsg()
    data class SelectedWordsChanged(val wordsList: List<String>) : TasksMsg()
    data class SelectedLettersChanged(val lettersList: List<String>) : TasksMsg()
    data class SelectedWordChanged(val word: String) : TasksMsg()
    data class ActionButtonClicked(val data: String) : TasksMsg()
    object SkipButtonClicked : TasksMsg()
}