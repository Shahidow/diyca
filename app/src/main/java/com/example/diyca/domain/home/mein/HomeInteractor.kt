package com.example.diyca.domain.home.mein

import com.example.diyca.domain.home.models.Reward
import com.example.diyca.domain.home.models.DailyActivity
import com.example.diyca.domain.learning.models.LessonSection
import kotlinx.coroutines.flow.Flow

interface HomeInteractor {
    fun getUserName(): Flow<String>
    suspend fun getLesson(): LessonSection
    fun getDailyActivity(): Flow<DailyActivity?>
    fun getRewards(): Flow<List<Reward>>
}