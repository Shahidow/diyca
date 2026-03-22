package com.example.speak_caucasus.data.repository.userdata.impl

import com.example.speak_caucasus.data.db.userdata.UserDataConverter
import com.example.speak_caucasus.data.db.userdata.UserDatabase
import com.example.speak_caucasus.data.db.userdata.entity.ActivityEntity
import com.example.speak_caucasus.data.prefs.UserPrefsRepository
import com.example.speak_caucasus.data.repository.userdata.UserDataRepository
import com.example.speak_caucasus.domain.home.models.DailyActivity
import com.example.speak_caucasus.domain.home.models.Reward
import com.example.speak_caucasus.domain.learning.models.UserProgress
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class UserDataRepositoryImpl(
    private val userPrefsRepository: UserPrefsRepository,
    private val userDatabase: UserDatabase,
    private val userDataConverter: UserDataConverter
) :
    UserDataRepository {
    override fun getUserName(): Flow<String> = userPrefsRepository.getUserNameFlow()
    override suspend  fun insertUserName(name: String) = userPrefsRepository.saveUserName(name)
    override fun getUserEmail(): Flow<String> = userPrefsRepository.getUserEmailFlow()
    override suspend  fun insertUserEmail(email: String) = userPrefsRepository.saveUserEmail(email)

    override fun getAllActivity(): Flow<List<DailyActivity>> =
        userDatabase.activityDao().getAllActivity()
            .map { list -> list.map { userDataConverter.mapUserActivity(it) } }
            .catch { emit(emptyList()) }

    override fun getActivityFromDate(startDate: Long): Flow<List<DailyActivity>> =
        userDatabase.activityDao().getActivityFromDate(startDate)
            .map { list -> list.map { userDataConverter.mapUserActivity(it) } }
            .catch { emit(emptyList()) }

    override suspend fun insertActivity(startOfDay: Long, lessonsToAdd: Int, tasksToAdd: Int) {
        val existingActivity = userDatabase.activityDao().getActivityByDate(startOfDay)
        if (existingActivity != null) {
            val updatedActivity = existingActivity.copy(
                lessonsCompleted = existingActivity.lessonsCompleted + lessonsToAdd,
                tasksCompleted = existingActivity.tasksCompleted + tasksToAdd
            )
            userDatabase.activityDao().insertActivity(updatedActivity)
        } else {
            val newActivity = ActivityEntity(
                date = startOfDay,
                lessonsCompleted = lessonsToAdd,
                tasksCompleted = tasksToAdd
            )
            userDatabase.activityDao().insertActivity(newActivity)
        }
    }

    override fun getTodayActivity(startOfDay: Long): Flow<DailyActivity?> {
        return userDatabase.activityDao().getActivityFromDate(startOfDay).map { list ->
            list.firstOrNull()?.let { userDataConverter.mapUserActivity(it) }
        }
    }

    override fun getAllRewards(): Flow<List<Reward>> =
        userDatabase.rewardsDao().getAllRewards()
            .map { list -> list.map { userDataConverter.mapUserReward(it) } }
            .catch { emit(emptyList()) }

    override suspend fun insertReward(reward: Reward) {
        userDatabase.rewardsDao().insertReward(userDataConverter.mapUserReward(reward))
    }

    override fun getLessonCount(lessonId: String): Flow<Int> =
        userDatabase.progressDao().getLessonCount(lessonId).catch { emit(0) }

    override fun getThemeCount(themeId: String): Flow<Int> =
        userDatabase.progressDao().getThemeCount(themeId).catch { emit(0) }

    override fun getLessonTaskIds(lessonId: String): Flow<List<String>> =
        userDatabase.progressDao().getLessonTaskIds(lessonId).catch { emit(emptyList()) }

    override fun getProgressByTheme(themeId: String): Flow<List<UserProgress>> =
        userDatabase.progressDao().getProgressByTheme(themeId)
            .map { list -> list.map { userDataConverter.mapUserProgress(it) } }
            .catch { emit(emptyList()) }

    override suspend fun insertUserProgress(userProgress: UserProgress) {
        userDatabase.progressDao().insertProgress(userDataConverter.mapUserProgress(userProgress))
    }
}