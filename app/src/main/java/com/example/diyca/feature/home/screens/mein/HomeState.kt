package com.example.diyca.feature.home.screens.mein

import com.example.diyca.domain.home.models.Reward
import com.example.diyca.domain.home.models.DailyActivity
import com.example.diyca.domain.learning.models.LessonSection

data class HomeState(
    val rewards: List<Reward> = emptyList(),
    val dailyActivity: DailyActivity? = null,
    val userName: String = "",
    val isLoading: Boolean = true,
    val error: String? = null,
    val todayLesson: LessonSection? = null,
    val showConfirmation: Boolean = false
)
