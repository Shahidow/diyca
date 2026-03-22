package com.example.speak_caucasus.data.repository.userdata

import com.example.speak_caucasus.domain.home.models.DailyActivity
import com.example.speak_caucasus.domain.home.models.Reward
import com.example.speak_caucasus.domain.learning.models.UserProgress
import kotlinx.coroutines.flow.Flow

interface UserDataRepository {
    fun getUserName(): Flow<String>
    suspend fun insertUserName(name: String)
    fun getUserEmail(): Flow<String>
    suspend fun insertUserEmail(email: String)

    fun getAllActivity() : Flow<List<DailyActivity>>
    fun getActivityFromDate(startDate: Long) : Flow<List<DailyActivity>>
    fun getTodayActivity(startOfDay: Long): Flow<DailyActivity?>
    suspend fun insertActivity(startOfDay: Long, lessonsToAdd: Int, tasksToAdd: Int)

    fun getAllRewards(): Flow<List<Reward>>
    suspend fun insertReward(reward: Reward)

    fun getLessonCount(lessonId: String): Flow<Int>
    fun getThemeCount(themeId: String): Flow<Int>
    fun getLessonTaskIds(lessonId: String): Flow<List<String>>
    fun getProgressByTheme(themeId: String): Flow<List<UserProgress>>
    suspend fun insertUserProgress(userProgress: UserProgress)
}