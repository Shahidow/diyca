package com.example.speak_caucasus.di

import com.example.speak_caucasus.domain.dictionaries.dictionary.DictionaryInteractor
import com.example.speak_caucasus.domain.dictionaries.dictionary.impl.DictionaryInteractorImpl
import com.example.speak_caucasus.domain.favorites.FavoritesInteractor
import com.example.speak_caucasus.domain.favorites.impl.FavoritesInteractorImpl
import com.example.speak_caucasus.domain.home.mein.HomeInteractor
import com.example.speak_caucasus.domain.home.mein.impl.HomeInteractorImpl
import com.example.speak_caucasus.domain.home.profile.ProfileInteractor
import com.example.speak_caucasus.domain.home.profile.impl.ProfileInteractorImpl
import com.example.speak_caucasus.domain.home.settings.SettingsInteractor
import com.example.speak_caucasus.domain.home.settings.impl.SettingsInteractorImpl
import com.example.speak_caucasus.domain.learning.lesson.LessonInteractor
import com.example.speak_caucasus.domain.learning.lesson.impl.LessonInteractorImpl
import com.example.speak_caucasus.domain.learning.study_plan.StudyPlanInteractor
import com.example.speak_caucasus.domain.learning.study_plan.impl.StudyPlanInteractorImpl
import com.example.speak_caucasus.domain.phrasebooks.PhrasebookInteractor
import com.example.speak_caucasus.domain.phrasebooks.impl.PhrasebookInteractorImpl
import com.example.speak_caucasus.domain.auth.login.LoginInteractor
import com.example.speak_caucasus.domain.auth.login.impl.LoginInteractorImpl
import com.example.speak_caucasus.domain.auth.recovery.RecoveryInteractor
import com.example.speak_caucasus.domain.auth.recovery.RecoveryInteractorImpl
import com.example.speak_caucasus.domain.auth.registration.RegistrationInteractor
import com.example.speak_caucasus.domain.auth.registration.impl.RegistrationInteractorImpl
import com.example.speak_caucasus.domain.home.activity.ActivityInteractor
import com.example.speak_caucasus.domain.home.activity.impl.ActivityInteractorImpl
import com.example.speak_caucasus.domain.learning.tasks.TasksInteractor
import com.example.speak_caucasus.domain.learning.tasks.impl.TasksInteractorImpl
import com.example.speak_caucasus.domain.session.SessionManager
import com.example.speak_caucasus.domain.session.impl.SessionManagerImpl
import org.koin.dsl.module

val domainModule = module {
    single<SessionManager> { SessionManagerImpl(get()) }

    single<LoginInteractor> { LoginInteractorImpl(get()) }
    single<RegistrationInteractor> { RegistrationInteractorImpl(get()) }
    single<RecoveryInteractor> { RecoveryInteractorImpl(get()) }

    single<HomeInteractor> { HomeInteractorImpl(get()) }
    single<ProfileInteractor> { ProfileInteractorImpl() }
    single<SettingsInteractor> { SettingsInteractorImpl(get(), get()) }

    single<ActivityInteractor> { ActivityInteractorImpl(get()) }

    single<StudyPlanInteractor> { StudyPlanInteractorImpl() }
    single<LessonInteractor> { LessonInteractorImpl() }
    single<TasksInteractor> { TasksInteractorImpl() }

    single<FavoritesInteractor> { FavoritesInteractorImpl(get()) }

    single<DictionaryInteractor> { DictionaryInteractorImpl(get(), get()) }

    single<PhrasebookInteractor> { PhrasebookInteractorImpl(get()) }
}