package com.example.diyca.data.repository.userdata

import com.example.diyca.domain.home.models.DailyActivity
import com.example.diyca.domain.rewards.models.Reward
import com.example.diyca.domain.learning.models.UserProgress
import kotlinx.coroutines.flow.Flow

interface UserDataBaseRepository {
    fun getUserAvatar(): Flow<String>
    suspend fun insertUserAvatar(avatar: String)
    fun getUserName(): Flow<String>
    suspend fun insertUserName(name: String)
    fun getUserEmail(): Flow<String>
    suspend fun insertUserEmail(email: String)

    fun getAllActivity() : Flow<List<DailyActivity>>
    fun getActivityFromDate(date: String) : Flow<List<DailyActivity>>
    fun getTodayActivity(date: String): Flow<DailyActivity?>
    suspend fun insertActivity(dailyActivity: DailyActivity)

    fun getAllRewards(): Flow<List<Reward>>
    fun getUserRewards(): Flow<List<String>>
    suspend fun insertUserRewards(rewardTitles: List<String>)
    suspend fun insertReward(reward: Reward)
    suspend fun updateRewardImage(id: String, localPath: String)
    suspend fun clearAllRewards()

    fun getAllProgress(): Flow<List<UserProgress>>
    fun getProgressByLesson(lessonId: String): Flow<List<UserProgress>>
    fun getProgressByLessonCount(lessonId: String): Flow<Int>
    fun getProgressByTopic(topicId: String): Flow<List<UserProgress>>
    suspend fun insertUserProgress(userProgress: UserProgress)
    suspend fun clearProgress()

    suspend fun clearAllData()
}