package com.example.diyca.ui.navigation

import com.example.diyca.domain.dictionaries.dictionary.models.DictionaryType
import kotlinx.serialization.Serializable

sealed class ScreenRoutes {

    //GRAPHS
    @Serializable
    data object AuthGraph : ScreenRoutes()
    @Serializable
    data object MainGraph : ScreenRoutes()

    //BUTTONS
    @Serializable
    data object HomeRout : ScreenRoutes()
    @Serializable
    data object LearningRout : ScreenRoutes()
    @Serializable
    data object PhrasebookRout : ScreenRoutes()
    @Serializable
    data object LibraryRout : ScreenRoutes()
    @Serializable
    data object FavoritesRout : ScreenRoutes()

    //HOME
    @Serializable
    data object ProfileRout : ScreenRoutes()
    @Serializable
    data object SettingsRout : ScreenRoutes()

    //ACTIVITY
    @Serializable
    data object ActivityRout : ScreenRoutes()

    //START
    @Serializable
    data object LoginRout : ScreenRoutes()
    @Serializable
    data object RegistrationRout : ScreenRoutes()
    @Serializable
    data object RecoveryRout : ScreenRoutes()

    //LEARNING
    @Serializable
    data class LessonRout(
        val id: Int = 0,
        val title: String = "",
        val lessonsAmount: Int = 0,
        val newWordsAmount: Int = 0,
        val pic: Int = 0,
        val text: String = "",
        //val lessonsList: List<LessonSection>
    ) : ScreenRoutes()

    @Serializable
    data object SectionRout : ScreenRoutes()

    //DICTIONARY
    @Serializable
    data class DictionaryItemRout(
        val id: Int = 0,
        val isFavorites: Boolean = false,
        val type: DictionaryType
    ) : ScreenRoutes()
}