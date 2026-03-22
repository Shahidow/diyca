package com.example.speak_caucasus.feature.home.screens.mein

import com.example.speak_caucasus.domain.home.models.Reward
import com.example.speak_caucasus.domain.home.models.DailyActivity
import com.example.speak_caucasus.domain.learning.models.LessonSection

data class HomeState(
    val rewards: List<Reward> = emptyList(),
    val dailyActivity: DailyActivity? = null,
    val userName: String = "",
    val isLoading: Boolean = true,
    val error: String? = null,
    val todayLesson: LessonSection? = null,
    val showConfirmation: Boolean = false
)
