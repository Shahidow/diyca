package com.example.speak_caucasus.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.toRoute
import com.example.speak_caucasus.feature.favorites.screens.favorites.FavoritesScreen
import com.example.speak_caucasus.feature.home.screens.mein.HomeScreen
import com.example.speak_caucasus.feature.home.screens.profile.ProfileScreen
import com.example.speak_caucasus.feature.home.screens.settings.SettingsScreen
import com.example.speak_caucasus.feature.learning.screens.lesson.LessonScreen
import com.example.speak_caucasus.feature.learning.screens.section.SectionScreen
import com.example.speak_caucasus.feature.learning.screens.study_plan.StudyPlanScreen
import com.example.speak_caucasus.feature.dictionaries.screens.dictionary.DictionaryScreen
import com.example.speak_caucasus.feature.dictionaries.screens.dictionary_item.DictionaryItemScreen
import com.example.speak_caucasus.feature.home.screens.activity.ActivityScreen
import com.example.speak_caucasus.feature.phrasebooks.screens.phrasebook.PhrasebookScreen

fun NavGraphBuilder.mainNavGraph(
    navHostController: NavHostController,
    paddingValues: PaddingValues
) {
    navigation<ScreenRoutes.MainGraph>(
        startDestination = ScreenRoutes.HomeRout
    ) {
        composable<ScreenRoutes.HomeRout> { HomeScreen(navHostController) }
        composable<ScreenRoutes.LearningRout> { StudyPlanScreen(navHostController) }
        composable<ScreenRoutes.PhrasebookRout> { PhrasebookScreen() }
        composable<ScreenRoutes.LibraryRout> { DictionaryScreen(navHostController) }
        composable<ScreenRoutes.FavoritesRout> { FavoritesScreen(navHostController) }

        composable<ScreenRoutes.ProfileRout> { ProfileScreen(navHostController) }
        composable<ScreenRoutes.SettingsRout> { SettingsScreen(navHostController) }

        composable<ScreenRoutes.ActivityRout> { ActivityScreen(navHostController) }

        composable<ScreenRoutes.LessonRout> { backStackEntry ->
            val lesson: ScreenRoutes.LessonRout = backStackEntry.toRoute<ScreenRoutes.LessonRout>()
            LessonScreen(navHostController, lesson)
        }
        composable<ScreenRoutes.SectionRout> { SectionScreen() }

        composable<ScreenRoutes.DictionaryItemRout> { backStackEntry ->
            val itemData: ScreenRoutes.DictionaryItemRout =
                backStackEntry.toRoute<ScreenRoutes.DictionaryItemRout>()
            DictionaryItemScreen(navHostController, itemData)
        }
    }
}