package com.example.diyca.feature.home.screens.mein

import com.example.diyca.domain.home.models.Reward
import com.example.diyca.domain.home.models.DailyActivity
import com.example.diyca.domain.home.settings.models.UserAvatar
import com.example.diyca.domain.learning.models.Lesson
import com.example.diyca.util.ErrorType

data class HomeState(
    val avatar: UserAvatar? = null,
    val rewards: List<Reward> = emptyList(),
    val dailyActivity: DailyActivity? = null,
    val userName: String = "",
    val isLoading: Boolean = true,
    val isRefreshing: Boolean = false,
    val error: ErrorType? = null,
    val todayLesson: Lesson? = null,
    val todayLessonTopicId: String = "",
    val isCourseFinished: Boolean = false,
    val showConfirmation: Boolean = false
)
