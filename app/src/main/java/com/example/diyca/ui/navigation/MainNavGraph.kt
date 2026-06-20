package com.example.diyca.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.toRoute
import com.example.diyca.feature.favorites.screens.favorites.FavoritesScreen
import com.example.diyca.feature.home.screens.mein.HomeScreen
import com.example.diyca.feature.home.screens.profile.ProfileScreen
import com.example.diyca.feature.home.screens.settings.SettingsScreen
import com.example.diyca.feature.learning.screens.topic.TopicScreen
import com.example.diyca.feature.learning.screens.lesson.LessonScreen
import com.example.diyca.feature.learning.screens.study_plan.StudyPlanScreen
import com.example.diyca.feature.dictionaries.screens.dictionary.DictionaryScreen
import com.example.diyca.feature.dictionaries.screens.dictionary_item.DictionaryItemScreen
import com.example.diyca.feature.home.screens.activity.ActivityScreen
import com.example.diyca.feature.home.screens.activity_calendar.ActivityCalendarScreen
import com.example.diyca.feature.learning.screens.tasks.TasksScreen
import com.example.diyca.feature.learning.screens.tasks_result.TasksResultScreen
import com.example.diyca.feature.phrasebooks.screens.phrasebook.PhrasebookScreen
import com.example.diyca.feature.phrasebooks.screens.phrasebook_items_list.PhrasebookItemsScreen

fun NavGraphBuilder.mainNavGraph(
    navHostController: NavHostController
) {
    navigation<ScreenRoutes.MainGraph>(
        startDestination = ScreenRoutes.BottomBarGraph
    ) {
        navigation<ScreenRoutes.BottomBarGraph>(
            startDestination = ScreenRoutes.HomeRout
        ) {
            composable<ScreenRoutes.HomeRout> { HomeScreen(navHostController) }
            composable<ScreenRoutes.LearningRout> { StudyPlanScreen(navHostController) }
            composable<ScreenRoutes.PhrasebookRout> { PhrasebookScreen(navHostController) }
            composable<ScreenRoutes.LibraryRout> { DictionaryScreen(navHostController) }
            composable<ScreenRoutes.FavoritesRout> { FavoritesScreen(navHostController) }
        }

        composable<ScreenRoutes.ProfileRout> { ProfileScreen(navHostController) }
        composable<ScreenRoutes.SettingsRout> { SettingsScreen(navHostController) }

        composable<ScreenRoutes.ActivityRout> { ActivityScreen(navHostController) }
        composable<ScreenRoutes.ActivityCalendarRout> { ActivityCalendarScreen(navHostController) }

        composable<ScreenRoutes.TopicRout> { backStackEntry ->
            val topic: ScreenRoutes.TopicRout = backStackEntry.toRoute<ScreenRoutes.TopicRout>()
            TopicScreen(navHostController, topic)
        }
        composable<ScreenRoutes.LessonRout> { backStackEntry ->
            val lesson: ScreenRoutes.LessonRout = backStackEntry.toRoute<ScreenRoutes.LessonRout>()
            LessonScreen(navHostController, lesson)
        }
        composable<ScreenRoutes.TasksRout> { backStackEntry ->
            val tasks: ScreenRoutes.TasksRout = backStackEntry.toRoute<ScreenRoutes.TasksRout>()
            TasksScreen(navHostController, tasks)
        }
        composable<ScreenRoutes.TasksResultRout> { backStackEntry ->
            val tasksResult: ScreenRoutes.TasksResultRout =
                backStackEntry.toRoute<ScreenRoutes.TasksResultRout>()
            TasksResultScreen(navHostController, tasksResult)
        }

        composable<ScreenRoutes.PhrasebookItemsRout> { backStackEntry ->
            val parentId: ScreenRoutes.PhrasebookItemsRout =
                backStackEntry.toRoute<ScreenRoutes.PhrasebookItemsRout>()
            PhrasebookItemsScreen(navHostController, parentId)
        }

        composable<ScreenRoutes.DictionaryItemRout> { backStackEntry ->
            val itemData: ScreenRoutes.DictionaryItemRout =
                backStackEntry.toRoute<ScreenRoutes.DictionaryItemRout>()
            DictionaryItemScreen(navHostController, itemData)
        }
    }
}