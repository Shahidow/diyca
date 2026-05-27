package com.example.diyca.feature.learning.screens.tasks_result

import com.example.diyca.ui.navigation.ScreenRoutes
import com.example.diyca.util.ErrorType

sealed class TasksResultMsg {
    data class LoadTasksResult(val tasksResultRout: ScreenRoutes.TasksResultRout) : TasksResultMsg()
    data object SetResult : TasksResultMsg()
    data class Error(val errorType: ErrorType) : TasksResultMsg()
    data object CloseClicked : TasksResultMsg()
    data class StartTasksClicked(val isContinue: Boolean) : TasksResultMsg()
}