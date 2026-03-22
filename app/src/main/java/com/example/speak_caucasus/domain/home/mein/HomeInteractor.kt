package com.example.speak_caucasus.domain.home.mein

import com.example.speak_caucasus.domain.home.models.Reward
import com.example.speak_caucasus.domain.home.models.DailyActivity
import com.example.speak_caucasus.domain.learning.models.LessonSection
import kotlinx.coroutines.flow.Flow

interface HomeInteractor {
    fun getUserName(): Flow<String>
    suspend fun getLesson(): LessonSection
    fun getDailyActivity(): Flow<DailyActivity?>
    fun getRewards(): Flow<List<Reward>>
}