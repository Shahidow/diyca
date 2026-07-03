package com.example.diyca.domain.home.mein

import com.example.diyca.domain.home.models.Reward
import com.example.diyca.domain.home.models.DailyActivity
import kotlinx.coroutines.flow.Flow

interface HomeInteractor {
    fun getUserAvatar(): Flow<String>
    fun getUserName(): Flow<String>
    fun getLesson(languageId: String): Flow<CurrentLessonState>
    fun retryGetLesson()
    fun getDailyActivity(): Flow<DailyActivity?>
    fun getUserRewards(): Flow<List<Reward>>
    suspend fun getRewards()
}