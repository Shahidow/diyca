package com.example.diyca.domain.learning.tasks_result.models

import com.example.diyca.domain.home.models.DailyActivity

data class TasksResultModel(
    val activity: DailyActivity?,
    val newRewards: List<String>
)
