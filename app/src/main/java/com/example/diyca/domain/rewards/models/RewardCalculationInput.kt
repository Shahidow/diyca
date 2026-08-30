package com.example.diyca.domain.rewards.models

import com.example.diyca.domain.home.models.DailyActivity
import com.example.diyca.domain.learning.models.UserProgress

data class RewardCalculationInput(
    val currentActivity: List<DailyActivity>,
    val currentProgress: List<UserProgress>,
    val alreadyEarnedIds: Set<String>,
    val currentLessonResult: LessonResult,
    val allAvailableRewards: List<Reward>
)

data class LessonResult(
    val lessonId: String,
    val topicId: String,
    val isTopicCompleted: Boolean,
    val isLessonCompleted: Boolean,
    val lessonTasksCount: Int,
    val completedTasks: List<String>,
    val date: String
)
