package com.example.diyca.feature.learning.screens.tasks

import com.example.diyca.ui.navigation.ScreenRoutes
import com.example.diyca.util.ErrorType


sealed class TasksMsg {
    data class LoadData(val tasksRout: ScreenRoutes.TasksRout) : TasksMsg()
    data class Error(val errorType: ErrorType) : TasksMsg()
    data class SelectedWordsChanged(val wordsList: List<String>) : TasksMsg()
    data class SelectedLettersChanged(val lettersList: List<String>) : TasksMsg()
    data class SelectedSingleWordChanged(val word: String) : TasksMsg()
    data class SelectedMultipleWordsChanged(val word: String) : TasksMsg()
    data class ActionButtonClicked(val data: String) : TasksMsg()
    data object CloseClicked : TasksMsg()
    data object CloseTasks : TasksMsg()
    data object DismissDialogs : TasksMsg()
    data object SkipButtonClicked : TasksMsg()
}