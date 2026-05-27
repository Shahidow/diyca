package com.example.diyca.feature.learning.screens.lesson

import com.example.diyca.ui.navigation.ScreenRoutes
import com.example.diyca.util.ErrorType

sealed class LessonMsg {
    data object DismissDialog : LessonMsg()
    data class Error(val errorType: ErrorType) : LessonMsg()
    data object StartTasksClicked : LessonMsg()
    data object BackClicked : LessonMsg()
    data class LoadLesson(val lessonRout: ScreenRoutes.LessonRout) : LessonMsg()
    data class StartTasks(val isContinue: Boolean) : LessonMsg()
}