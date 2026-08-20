package com.example.diyca.di

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import androidx.media3.common.util.UnstableApi
import androidx.media3.database.StandaloneDatabaseProvider
import androidx.media3.datasource.cache.LeastRecentlyUsedCacheEvictor
import androidx.media3.datasource.cache.SimpleCache
import androidx.room.Room
import com.example.diyca.data.db.dictionary.DictionaryDatabase
import com.example.diyca.data.db.dictionary.DictionaryItemConverter
import com.example.diyca.data.db.userdata.UserDataConverter
import com.example.diyca.data.db.userdata.UserDatabase
import com.example.diyca.data.mappers.AuthRequestMapper
import com.example.diyca.data.mappers.AuthResponseMapper
import com.example.diyca.data.mappers.DictionaryResponseMapper
import com.example.diyca.data.mappers.LearningResponseMapper
import com.example.diyca.data.mappers.UserDataMapper
import com.example.diyca.data.network.AuthApi
import com.example.diyca.data.network.AuthInterceptor
import com.example.diyca.data.network.DictionaryApi
import com.example.diyca.data.network.LearningApi
import com.example.diyca.data.network.TokenApi
import com.example.diyca.data.network.TokenAuthenticator
import com.example.diyca.data.network.UserApi
import com.example.diyca.data.prefs.UserPrefsRepository
import com.example.diyca.data.prefs.impl.UserPrefsRepositoryImpl
import com.example.diyca.data.repository.auth.AuthRepository
import com.example.diyca.data.repository.auth.TokenStorage
import com.example.diyca.data.repository.auth.impl.AuthRepositoryImpl
import com.example.diyca.data.repository.auth.impl.TokenStorageImpl
import com.example.diyca.data.repository.dictionaries.DictionaryDataBaseRepository
import com.example.diyca.data.repository.dictionaries.DictionaryNetworkRepository
import com.example.diyca.data.repository.dictionaries.impl.DictionaryDataBaseRepositoryImpl
import com.example.diyca.data.repository.dictionaries.impl.DictionaryNetworkRepositoryImpl
import com.example.diyca.data.repository.favorites.FavoritesRepository
import com.example.diyca.data.repository.favorites.impl.FavoritesRepositoryImpl
import com.example.diyca.data.repository.learning.LearningRepository
import com.example.diyca.data.repository.learning.impl.LearningRepositoryImpl
import com.example.diyca.data.repository.player.AudioRepository
import com.example.diyca.data.repository.player.impl.AudioRepositoryImpl
import com.example.diyca.data.repository.userdata.UserDataBaseRepository
import com.example.diyca.data.repository.userdata.UserNetworkRepository
import com.example.diyca.data.repository.userdata.impl.UserDataBaseRepositoryImpl
import com.example.diyca.data.repository.userdata.impl.UserNetworkRepositoryImpl
import com.example.diyca.util.BASE_URL
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File

val Context.dataStore by preferencesDataStore(name = "user_prefs")
@UnstableApi
val dataModule = module {
    single {
        SimpleCache(
            File(androidContext().cacheDir, "exoplayer_cache"),
            LeastRecentlyUsedCacheEvictor(100 * 1024 * 1024L),
            StandaloneDatabaseProvider(androidContext())
        )
    }
    single<AudioRepository> { AudioRepositoryImpl(get(), get()) }

    single { androidContext().dataStore }

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
    single<AuthApi> {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AuthApi::class.java)
    }
    single<UserApi> {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UserApi::class.java)
    }
    single<LearningApi> {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(LearningApi::class.java)
    }
    single<DictionaryApi> {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DictionaryApi::class.java)
    }
    single(named("refreshApi")) {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TokenApi::class.java)
    }

    factory { DictionaryItemConverter() }
    factory { UserDataConverter() }
    factory { UserDataMapper() }
    factory { LearningResponseMapper() }
    factory { DictionaryResponseMapper() }
    factory { AuthResponseMapper() }
    factory { AuthRequestMapper() }

    single<DictionaryNetworkRepository> { DictionaryNetworkRepositoryImpl(get(), get(), get()) }
    single<UserPrefsRepository> { UserPrefsRepositoryImpl(get()) }
    single<FavoritesRepository> { FavoritesRepositoryImpl(get(), get()) }
    single<DictionaryDataBaseRepository> { DictionaryDataBaseRepositoryImpl(get(), get()) }
    single<UserDataBaseRepository> { UserDataBaseRepositoryImpl(get(), get(), get()) }
    single<UserNetworkRepository> { UserNetworkRepositoryImpl(get(), get(), get()) }
    single<LearningRepository> { LearningRepositoryImpl(get(), get()) }

    single<AuthRepository> { AuthRepositoryImpl(get(), get(), get(), get()) }
}