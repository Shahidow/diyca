package com.example.speak_caucasus.di

import com.example.speak_caucasus.feature.dictionaries.screens.dictionary.DictionaryViewModel
import com.example.speak_caucasus.feature.favorites.screens.favorites.FavoritesViewModel
import com.example.speak_caucasus.feature.home.screens.mein.HomeViewModel
import com.example.speak_caucasus.feature.home.screens.profile.ProfileViewModel
import com.example.speak_caucasus.feature.home.screens.settings.SettingsViewModel
import com.example.speak_caucasus.feature.learning.screens.lesson.LessonViewModel
import com.example.speak_caucasus.feature.learning.screens.study_plan.StudyPlanViewModel
import com.example.speak_caucasus.feature.dictionaries.screens.dictionary_item.DictionaryItemViewModel
import com.example.speak_caucasus.feature.auth.screens.login.LoginViewModel
import com.example.speak_caucasus.feature.auth.screens.recovery.RecoveryViewModel
import com.example.speak_caucasus.feature.auth.screens.registration.RegistrationViewModel
import com.example.speak_caucasus.feature.home.screens.activity.ActivityViewModel
import com.example.speak_caucasus.feature.learning.screens.tasks.TasksViewModel
import com.example.speak_caucasus.feature.phrasebooks.screens.phrasebook.PhrasebookViewModel
import com.example.speak_caucasus.feature.root.AppViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel<AppViewModel> {AppViewModel(get())}

    viewModel<LoginViewModel> { LoginViewModel(get()) }
    viewModel<RegistrationViewModel> { RegistrationViewModel(get()) }
    viewModel<RecoveryViewModel> { RecoveryViewModel(get()) }

    viewModel<HomeViewModel> { HomeViewModel(get()) }

    viewModel<ProfileViewModel> { ProfileViewModel(get()) }
    viewModel<SettingsViewModel> { SettingsViewModel(get(), get()) }

    viewModel<ActivityViewModel> { ActivityViewModel(get()) }

    viewModel<StudyPlanViewModel> { StudyPlanViewModel(get()) }
    viewModel<LessonViewModel> { LessonViewModel(get()) }
    viewModel<TasksViewModel> { TasksViewModel(get()) }

    viewModel<PhrasebookViewModel> { PhrasebookViewModel(get()) }

    viewModel<DictionaryViewModel> { DictionaryViewModel(get()) }
    viewModel<DictionaryItemViewModel> { DictionaryItemViewModel(get(), get()) }

    viewModel<FavoritesViewModel> { FavoritesViewModel(get()) }
}