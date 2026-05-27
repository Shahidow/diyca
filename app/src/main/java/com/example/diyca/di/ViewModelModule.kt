package com.example.diyca.di

import com.example.diyca.feature.dictionaries.screens.dictionary.DictionaryViewModel
import com.example.diyca.feature.favorites.screens.favorites.FavoritesViewModel
import com.example.diyca.feature.home.screens.mein.HomeViewModel
import com.example.diyca.feature.home.screens.profile.ProfileViewModel
import com.example.diyca.feature.home.screens.settings.SettingsViewModel
import com.example.diyca.feature.learning.screens.topic.TopicViewModel
import com.example.diyca.feature.learning.screens.study_plan.StudyPlanViewModel
import com.example.diyca.feature.dictionaries.screens.dictionary_item.DictionaryItemViewModel
import com.example.diyca.feature.auth.screens.login.LoginViewModel
import com.example.diyca.feature.auth.screens.recovery.RecoveryViewModel
import com.example.diyca.feature.auth.screens.registration.RegistrationViewModel
import com.example.diyca.feature.home.screens.activity.ActivityViewModel
import com.example.diyca.feature.home.screens.activity_calendar.ActivityCalendarViewModel
import com.example.diyca.feature.learning.screens.lesson.LessonViewModel
import com.example.diyca.feature.learning.screens.tasks.TasksViewModel
import com.example.diyca.feature.learning.screens.tasks_result.TasksResultViewModel
import com.example.diyca.feature.phrasebooks.screens.phrasebook.PhrasebookViewModel
import com.example.diyca.feature.phrasebooks.screens.phrasebook_items_list.PhrasebookItemsViewModel
import com.example.diyca.feature.root.AppViewModel
import com.example.diyca.feature.startup.screen.StartupViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel<AppViewModel> { AppViewModel(get(), get()) }

    viewModel<StartupViewModel> { StartupViewModel(get()) }

    viewModel<LoginViewModel> { LoginViewModel(get()) }
    viewModel<RegistrationViewModel> { RegistrationViewModel(get()) }
    viewModel<RecoveryViewModel> { RecoveryViewModel(get()) }

    viewModel<HomeViewModel> { HomeViewModel(get()) }

    viewModel<ProfileViewModel> { ProfileViewModel(get()) }
    viewModel<SettingsViewModel> { SettingsViewModel(get()) }

    viewModel<ActivityViewModel> { ActivityViewModel(get()) }
    viewModel<ActivityCalendarViewModel> { ActivityCalendarViewModel(get()) }

    viewModel<StudyPlanViewModel> { StudyPlanViewModel(get()) }
    viewModel<TopicViewModel> { TopicViewModel(get()) }
    viewModel<LessonViewModel> { LessonViewModel(get()) }
    viewModel<TasksViewModel> { TasksViewModel(get()) }
    viewModel<TasksResultViewModel> { TasksResultViewModel(get()) }

    viewModel<PhrasebookViewModel> { PhrasebookViewModel(get()) }
    viewModel<PhrasebookItemsViewModel> { PhrasebookItemsViewModel(get()) }

    viewModel<DictionaryViewModel> { DictionaryViewModel(get()) }
    viewModel<DictionaryItemViewModel> { DictionaryItemViewModel(get(), get(), get()) }

    viewModel<FavoritesViewModel> { FavoritesViewModel(get()) }
}