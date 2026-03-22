package com.example.speak_caucasus.domain.home.activity

import com.example.speak_caucasus.domain.home.models.DailyActivity

interface ActivityInteractor {
    suspend fun getWeeklyActivities(mondayTimestamp: Long): List<DailyActivity>
}