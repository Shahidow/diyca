package com.example.diyca.domain.home.activity.impl

import com.example.diyca.data.repository.userdata.UserDataRepository
import com.example.diyca.domain.home.activity.ActivityInteractor
import com.example.diyca.domain.home.models.DailyActivity
import kotlinx.coroutines.flow.firstOrNull

class ActivityInteractorImpl(private val userDataRepository: UserDataRepository): ActivityInteractor {
    override suspend fun getWeeklyActivities(mondayTimestamp: Long): List<DailyActivity> {
        return userDataRepository.getActivityFromDate(mondayTimestamp).firstOrNull() ?: emptyList()
    }
}