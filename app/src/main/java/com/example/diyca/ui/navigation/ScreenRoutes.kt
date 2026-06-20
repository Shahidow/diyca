package com.example.diyca.ui.navigation

import com.example.diyca.domain.dictionaries.dictionary.models.DictionaryType
import kotlinx.serialization.Serializable

sealed class ScreenRoutes {

    //GRAPHS
    @Serializable
    data object AuthGraph : ScreenRoutes()

    @Serializable
    data object MainGraph : ScreenRoutes()

    @Serializable
    data object BottomBarGraph

    @Serializable
    data object DownloadGraph : ScreenRoutes()

    //STARTUP
    @Serializable
    data object StartupRout : ScreenRoutes()

    //AUTH
    @Serializable
    data object LoginRout : ScreenRoutes()

    @Serializable
    data object RegistrationRout : ScreenRoutes()

    @Serializable
    data object RecoveryRout : ScreenRoutes()

    //PHRASEBOOKS
    @Serializable
    data object PhrasebookRout : ScreenRoutes()

    @Serializable
    data class PhrasebookItemsRout(
        val parentId: Int = 0
    ) : ScreenRoutes()

    //HOME
    @Serializable
    data object HomeRout : ScreenRoutes()

    @Serializable
    data object ProfileRout : ScreenRoutes()

    @Serializable
    data object SettingsRout : ScreenRoutes()

    //ACTIVITY
    @Serializable
    data object ActivityRout : ScreenRoutes()

    @Serializable
    data object ActivityCalendarRout : ScreenRoutes()

    //LEARNING
    @Serializable
    data object LearningRout : ScreenRoutes()

    @Serializable
    data class TopicRout(
        val id: String = "",
        val header: String = "",
        val audio: String? = null,
        val text: String = "",
    ) : ScreenRoutes()

    @Serializable
    data class LessonRout(
        val id: String = "",
        val topicId: String = "",
        val number: Int = 0,
        val title: String = "",
        val text: String = "",
        val image: String? = null,
        val audio: String? = null,
        val tasksCount: Int = 0,
    ) : ScreenRoutes()

    @Serializable
    data class TasksRout(
        val topicId: String = "",
        val lessonId: String = "",
        val isContinue: Boolean = false,
        val lessonTasksCount: Int = 0
    ) : ScreenRoutes()

    @Serializable
    data class TasksResultRout(
        val topicId: String = "",
        val lessonId: String = "",
        val completedTasks: List<String> = emptyList(),
        val tasksCount: Int = 0,
        val lessonTasksCount: Int = 0,
    ) : ScreenRoutes()

    //DICTIONARY
    @Serializable
    data object LibraryRout : ScreenRoutes()

    @Serializable
    data class DictionaryItemRout(
        val id: Int = 0,
        val isFavorites: Boolean = false,
        val type: DictionaryType,
        val parentId: Int? = null
    ) : ScreenRoutes()

    //FAVORITES
    @Serializable
    data object FavoritesRout : ScreenRoutes()
}