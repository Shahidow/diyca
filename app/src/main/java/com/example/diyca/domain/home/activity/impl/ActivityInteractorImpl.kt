package com.example.diyca.domain.home.activity.impl

import com.example.diyca.data.repository.userdata.UserDataBaseRepository
import com.example.diyca.domain.home.activity.ActivityInteractor
import com.example.diyca.domain.home.models.DailyActivity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull

class ActivityInteractorImpl(private val userDataBaseRepository: UserDataBaseRepository): ActivityInteractor {
    override suspend fun getWeeklyActivities(date: String): List<DailyActivity> {
        return userDataBaseRepository.getActivityFromDate(date).firstOrNull() ?: emptyList()
    }

    override suspend fun getActivities(): List<DailyActivity> {
        return userDataBaseRepository.getAllActivity().first()
    }
}