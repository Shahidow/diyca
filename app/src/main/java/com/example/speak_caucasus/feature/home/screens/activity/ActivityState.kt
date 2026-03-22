package com.example.speak_caucasus.feature.home.screens.activity

import com.example.speak_caucasus.domain.home.models.DailyActivity

data class ActivityState(
    val isLoaded: Boolean = false,
    val activities: List<DailyActivity?> = emptyList(),
    val todayActivity: DailyActivity? = null,
    val isLoading: Boolean = true,
    val todayDate: String = ""
)
