package com.example.diyca.data.repository.userdata.impl

import com.example.diyca.data.db.userdata.UserDataConverter
import com.example.diyca.data.db.userdata.UserDatabase
import com.example.diyca.data.prefs.UserPrefsRepository
import com.example.diyca.data.repository.userdata.UserDataBaseRepository
import com.example.diyca.domain.home.models.DailyActivity
import com.example.diyca.domain.home.models.Reward
import com.example.diyca.domain.learning.models.UserProgress
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class UserDataBaseRepositoryImpl(
    private val userPrefsRepository: UserPrefsRepository,
    private val userDatabase: UserDatabase,
    private val userDataConverter: UserDataConverter
) :
    UserDataBaseRepository {
    override fun getUserAvatar(): Flow<String> = userPrefsRepository.getUserAvatarFlow()
    override suspend fun insertUserAvatar(avatar: String) =
        userPrefsRepository.saveUserAvatar(avatar)

    override fun getUserName(): Flow<String> = userPrefsRepository.getUserNameFlow()
    override suspend fun insertUserName(name: String) = userPrefsRepository.saveUserName(name)
    override fun getUserEmail(): Flow<String> = userPrefsRepository.getUserEmailFlow()
    override suspend fun insertUserEmail(email: String) = userPrefsRepository.saveUserEmail(email)

    override fun getAllActivity(): Flow<List<DailyActivity>> =
        userDatabase.activityDao().getAllActivity()
            .map { list -> list.map { userDataConverter.mapUserActivity(it) } }
            .catch { emit(emptyList()) }

    override fun getActivityFromDate(date: String): Flow<List<DailyActivity>> =
        userDatabase.activityDao().getActivityFromDate(date)
            .map { list -> list.map { userDataConverter.mapUserActivity(it) } }
            .catch { emit(emptyList()) }

    override suspend fun insertActivity(dailyActivity: DailyActivity) {
        userDatabase.activityDao().insertActivity(userDataConverter.mapUserActivity(dailyActivity))
    }

    override fun getTodayActivity(date: String): Flow<DailyActivity?> {
        return userDatabase.activityDao().getActivityFromDate(date).map { list ->
            list.firstOrNull()?.let { userDataConverter.mapUserActivity(it) }
        }
    }

    override fun getAllRewards(): Flow<List<Reward>> =
        userDatabase.rewardsDao().getAllRewards()
            .map { list -> list.map { userDataConverter.mapUserReward(it) } }
            .catch { emit(emptyList()) }

    override fun getUserRewards(): Flow<List<String>> = userPrefsRepository.getUserRewards()

    override suspend fun insertUserRewards(rewardTitles: List<String>) {
        userPrefsRepository.saveUserRewards(rewardTitles)
    }

    override suspend fun insertReward(reward: Reward) {
        userDatabase.rewardsDao().insertReward(userDataConverter.mapUserReward(reward))
    }

    override suspend fun updateRewardImage(id: String, localPath: String) {
        userDatabase.rewardsDao().updateRewardImage(id, localPath)
    }

    override suspend fun clearAllRewards() {
        userDatabase.rewardsDao().clearAllRewards()
    }

    override fun getAllProgress(): Flow<List<UserProgress>> =
        userDatabase.progressDao().getAllProgress()
            .map { list -> list.map { userDataConverter.mapUserProgress(it) } }
            .catch { emit(emptyList()) }

    override fun getProgressByLesson(lessonId: String): Flow<List<UserProgress>> =
        userDatabase.progressDao().getProgressByLesson(lessonId)
            .map { list -> list.map { userDataConverter.mapUserProgress(it) } }
            .catch { emit(emptyList()) }

    override fun getProgressByLessonCount(lessonId: String): Flow<Int> =
        userDatabase.progressDao().getLessonTasksCount(lessonId).catch { emit(0) }

    override fun getProgressByTopic(topicId: String): Flow<List<UserProgress>> =
        userDatabase.progressDao().getProgressByTheme(topicId)
            .map { list -> list.map { userDataConverter.mapUserProgress(it) } }
            .catch { emit(emptyList()) }

    override suspend fun clearProgress() {
        userDatabase.progressDao().clearAllProgress()
    }

    override suspend fun insertUserProgress(userProgress: UserProgress) {
        userDatabase.progressDao().insertProgress(userDataConverter.mapUserProgress(userProgress))
    }

    override suspend fun clearAllData() {
        userDatabase.progressDao().clearAllProgress()
        userDatabase.activityDao().clearAllActivity()
        userPrefsRepository.clearUserData()
    }
}