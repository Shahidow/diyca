package com.example.diyca.domain.home.activity

import com.example.diyca.domain.home.models.DailyActivity

interface ActivityInteractor {
    suspend fun getWeeklyActivities(date: String): List<DailyActivity>
    suspend fun getActivities(): List<DailyActivity>
}