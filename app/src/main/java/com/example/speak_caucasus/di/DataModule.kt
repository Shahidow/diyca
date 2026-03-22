package com.example.speak_caucasus.di

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.example.speak_caucasus.data.db.dictionary.DictionaryDatabase
import com.example.speak_caucasus.data.db.dictionary.DictionaryItemConverter
import com.example.speak_caucasus.data.db.userdata.UserDataConverter
import com.example.speak_caucasus.data.db.userdata.UserDatabase
import com.example.speak_caucasus.data.mappers.AuthRequestMapper
import com.example.speak_caucasus.data.mappers.AuthResponseMapper
import com.example.speak_caucasus.data.network.AuthApi
import com.example.speak_caucasus.data.network.AuthInterceptor
import com.example.speak_caucasus.data.network.TokenAuthenticator
import com.example.speak_caucasus.data.network.UserApi
import com.example.speak_caucasus.data.prefs.UserPrefsRepository
import com.example.speak_caucasus.data.prefs.UserPrefsRepositoryImpl
import com.example.speak_caucasus.data.repository.auth.AuthRepository
import com.example.speak_caucasus.data.repository.auth.TokenStorage
import com.example.speak_caucasus.data.repository.auth.impl.AuthRepositoryImpl
import com.example.speak_caucasus.data.repository.auth.impl.TokenStorageImpl
import com.example.speak_caucasus.data.repository.dictionaries.DictionaryRepository
import com.example.speak_caucasus.data.repository.dictionaries.DictionaryRepositoryImpl
import com.example.speak_caucasus.data.repository.favorites.FavoritesRepository
import com.example.speak_caucasus.data.repository.favorites.FavoritesRepositoryImpl
import com.example.speak_caucasus.data.repository.userdata.UserDataRepository
import com.example.speak_caucasus.data.repository.userdata.impl.UserDataRepositoryImpl
import com.example.speak_caucasus.util.BASE_URL
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val Context.dataStore by preferencesDataStore(name = "user_prefs")

val dataModule = module {

    single { androidContext().dataStore }
    single<UserPrefsRepository> { UserPrefsRepositoryImpl(get()) }
    single {
        Room.databaseBuilder(androidContext(), DictionaryDatabase::class.java, "dictionary.db")
            .build()
    }

    single {
        Room.databaseBuilder(androidContext(), UserDatabase::class.java, "user.db")
            .build()
    }
    single<TokenStorage> { TokenStorageImpl(androidContext()) }
    single { AuthInterceptor(get()) }
    single { TokenAuthenticator(get(), get(named("refreshApi"))) }

    single {
        OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(get()))
            .authenticator(TokenAuthenticator(get(), get(named("refreshApi"))))
            .build()
    }
    single<UserApi> {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UserApi::class.java)
    }
    single(named("refreshApi")) {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AuthApi::class.java)
    }

    factory { DictionaryItemConverter() }
    factory { UserDataConverter() }
    single<FavoritesRepository> { FavoritesRepositoryImpl(get(), get()) }
    single<DictionaryRepository> { DictionaryRepositoryImpl(get(), get()) }
    single<UserDataRepository> { UserDataRepositoryImpl(get(), get(), get()) }

    factory { AuthResponseMapper() }
    factory { AuthRequestMapper() }
    single<AuthRepository> { AuthRepositoryImpl(get(), get(), get(), get(), get()) }
}