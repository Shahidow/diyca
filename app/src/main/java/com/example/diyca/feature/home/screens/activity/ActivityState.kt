package com.example.diyca.feature.home.screens.activity

import com.example.diyca.domain.home.models.DailyActivity

data class ActivityState(
    val isLoaded: Boolean = false,
    val activities: List<DailyActivity?> = emptyList(),
    val todayActivity: DailyActivity? = null,
    val isLoading: Boolean = true,
    val todayDate: String = ""
)
