package com.example.diyca.data.prefs.impl

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import com.example.diyca.data.prefs.UserPrefsRepository
import com.example.diyca.domain.home.settings.models.UserAvatar
import com.example.diyca.domain.startup.LibraryKeys
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class UserPrefsRepositoryImpl(
    private val dataStore: DataStore<Preferences>
) : UserPrefsRepository {

    companion object {
        private val KEY_USER_NAME = stringPreferencesKey("user_name")
        private val KEY_USER_EMAIL = stringPreferencesKey("user_email")
        private val KEY_USER_AVATAR = stringPreferencesKey("user_avatar")
        private val KEY_USER_REWARDS = stringSetPreferencesKey("user_rewards")
        private val KEY_WORDS_DB_VERSION = intPreferencesKey("words_db_version")
        private val KEY_PHRASES_DB_VERSION = intPreferencesKey("phrases_db_version")
        private val KEY_PROVERBS_DB_VERSION = intPreferencesKey("proverbs_db_version")
        private val KEY_EXPRESSIONS_DB_VERSION = intPreferencesKey("expressions_db_version")
        private val KEY_REWARDS_DB_VERSION = intPreferencesKey("rewards_db_version")
    }

    override fun getLibVersions(libKey: LibraryKeys): Flow<Int?> = dataStore.data
        .catch { exception -> if (exception is IOException) emit(emptyPreferences()) else throw exception }
        .map { prefs ->
            when(libKey){
                LibraryKeys.WORDS -> prefs[KEY_WORDS_DB_VERSION]
                LibraryKeys.PHRASES -> prefs[KEY_PHRASES_DB_VERSION]
                LibraryKeys.PROVERBS -> prefs[KEY_PROVERBS_DB_VERSION]
                LibraryKeys.EXPRESSIONS -> prefs[KEY_EXPRESSIONS_DB_VERSION]
                LibraryKeys.REWARDS -> prefs[KEY_REWARDS_DB_VERSION]
            }
        }

    override suspend fun saveLibVersion(libKey: LibraryKeys, version: Int) {
        val key = when (libKey) {
            LibraryKeys.WORDS -> KEY_WORDS_DB_VERSION
            LibraryKeys.PHRASES -> KEY_PHRASES_DB_VERSION
            LibraryKeys.PROVERBS -> KEY_PROVERBS_DB_VERSION
            LibraryKeys.EXPRESSIONS -> KEY_EXPRESSIONS_DB_VERSION
            LibraryKeys.REWARDS -> KEY_REWARDS_DB_VERSION
        }
        dataStore.edit { it[key] = version }
    }

    override fun getUserRewards(): Flow<List<String>> = dataStore.data
        .catch { exception -> if (exception is IOException) emit(emptyPreferences()) else throw exception }
        .map { prefs -> prefs[KEY_USER_REWARDS]?.toList() ?: emptyList() }

    override suspend fun saveUserRewards(rewardTitles: List<String>) {
        dataStore.edit { prefs ->
            val currentRewards = prefs[KEY_USER_REWARDS] ?: emptySet()
            prefs[KEY_USER_REWARDS] = currentRewards + rewardTitles
        }
    }

    override fun getUserAvatarFlow(): Flow<String> = dataStore.data
        .catch { exception -> if (exception is IOException) emit(emptyPreferences()) else throw exception }
        .map { preferences -> preferences[KEY_USER_AVATAR] ?: UserAvatar.DEFAULT_KEY }

    override suspend fun saveUserAvatar(avatarKey: String) {
        dataStore.edit { preferences -> preferences[KEY_USER_AVATAR] = avatarKey }
    }

    override fun getUserNameFlow(): Flow<String> = dataStore.data
        .catch { exception -> if (exception is IOException) emit(emptyPreferences()) else throw exception }
        .map { preferences -> preferences[KEY_USER_NAME] ?: "" }

    override suspend fun saveUserName(name: String) {
        dataStore.edit { preferences -> preferences[KEY_USER_NAME] = name }
    }

    override fun getUserEmailFlow(): Flow<String> = dataStore.data
        .catch { exception -> if (exception is IOException) emit(emptyPreferences()) else throw exception }
        .map { preferences -> preferences[KEY_USER_EMAIL] ?: "" }

    override suspend fun saveUserEmail(email: String) {
        dataStore.edit { preferences -> preferences[KEY_USER_EMAIL] = email }
    }

    override suspend fun clearUserData() {
        dataStore.edit { preferences ->
            preferences.remove(KEY_USER_NAME)
            preferences.remove(KEY_USER_EMAIL)
            preferences.remove(KEY_USER_AVATAR)
            preferences.remove(KEY_USER_REWARDS)
        }
    }
}