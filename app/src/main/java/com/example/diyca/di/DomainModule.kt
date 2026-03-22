package com.example.diyca.di

import com.example.diyca.domain.dictionaries.dictionary.DictionaryInteractor
import com.example.diyca.domain.dictionaries.dictionary.impl.DictionaryInteractorImpl
import com.example.diyca.domain.favorites.FavoritesInteractor
import com.example.diyca.domain.favorites.impl.FavoritesInteractorImpl
import com.example.diyca.domain.home.mein.HomeInteractor
import com.example.diyca.domain.home.mein.impl.HomeInteractorImpl
import com.example.diyca.domain.home.profile.ProfileInteractor
import com.example.diyca.domain.home.profile.impl.ProfileInteractorImpl
import com.example.diyca.domain.home.settings.SettingsInteractor
import com.example.diyca.domain.home.settings.impl.SettingsInteractorImpl
import com.example.diyca.domain.learning.lesson.LessonInteractor
import com.example.diyca.domain.learning.lesson.impl.LessonInteractorImpl
import com.example.diyca.domain.learning.study_plan.StudyPlanInteractor
import com.example.diyca.domain.learning.study_plan.impl.StudyPlanInteractorImpl
import com.example.diyca.domain.phrasebooks.PhrasebookInteractor
import com.example.diyca.domain.phrasebooks.impl.PhrasebookInteractorImpl
import com.example.diyca.domain.auth.login.LoginInteractor
import com.example.diyca.domain.auth.login.impl.LoginInteractorImpl
import com.example.diyca.domain.auth.recovery.RecoveryInteractor
import com.example.diyca.domain.auth.recovery.RecoveryInteractorImpl
import com.example.diyca.domain.auth.registration.RegistrationInteractor
import com.example.diyca.domain.auth.registration.impl.RegistrationInteractorImpl
import com.example.diyca.domain.home.activity.ActivityInteractor
import com.example.diyca.domain.home.activity.impl.ActivityInteractorImpl
import com.example.diyca.domain.learning.tasks.TasksInteractor
import com.example.diyca.domain.learning.tasks.impl.TasksInteractorImpl
import com.example.diyca.domain.session.SessionManager
import com.example.diyca.domain.session.impl.SessionManagerImpl
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