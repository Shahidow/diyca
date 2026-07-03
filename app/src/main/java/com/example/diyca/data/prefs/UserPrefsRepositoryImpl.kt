package com.example.diyca.data.prefs

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
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
        private val KEY_DB_VERSION_1 = stringPreferencesKey("db_version_lib1")
        private val KEY_DB_VERSION_2 = stringPreferencesKey("db_version_lib2")
        private val KEY_DB_VERSION_3 = stringPreferencesKey("db_version_lib3")
        private val KEY_DB_VERSION_4 = stringPreferencesKey("db_version_lib4")
    }

    override fun getLibVersionsFlow(): Flow<Map<String, String?>> = dataStore.data
        .catch { exception -> if (exception is IOException) emit(emptyPreferences()) else throw exception }
        .map { prefs ->
            mapOf(
                LibraryKeys.WORDS to prefs[KEY_DB_VERSION_1],
                LibraryKeys.PHRASES to prefs[KEY_DB_VERSION_2],
                LibraryKeys.PROVERBS to prefs[KEY_DB_VERSION_3],
                LibraryKeys.EXPRESSIONS to prefs[KEY_DB_VERSION_4]
            )
        }

    override suspend fun saveLibVersion(libKey: String, version: String) {
        val key = when (libKey) {
            LibraryKeys.WORDS -> KEY_DB_VERSION_1
            LibraryKeys.PHRASES -> KEY_DB_VERSION_2
            LibraryKeys.PROVERBS -> KEY_DB_VERSION_3
            LibraryKeys.EXPRESSIONS -> KEY_DB_VERSION_4
            else -> return
        }
        dataStore.edit { it[key] = version }
    }

    override fun getUserRewards(): Flow<List<String>> = dataStore.data
        .catch { exception -> if (exception is IOException) emit(emptyPreferences()) else throw exception }
        .map { prefs -> prefs[KEY_USER_REWARDS]?.toList() ?: emptyList() }

    override suspend fun insertUserReward(rewardTitle: String) {
        dataStore.edit { prefs ->
            val currentRewards = prefs[KEY_USER_REWARDS] ?: emptySet()
            prefs[KEY_USER_REWARDS] = currentRewards + rewardTitle
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