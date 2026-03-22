package com.example.diyca.domain.home.activity

import com.example.diyca.domain.home.models.DailyActivity

interface ActivityInteractor {
    suspend fun getWeeklyActivities(mondayTimestamp: Long): List<DailyActivity>
}