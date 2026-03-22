package com.example.diyca.domain.home.mein.impl

import com.example.diyca.data.repository.userdata.UserDataRepository
import com.example.diyca.domain.home.mein.HomeInteractor
import com.example.diyca.domain.home.models.Reward
import com.example.diyca.domain.home.models.DailyActivity
import com.example.diyca.domain.learning.models.LessonSection
import com.example.diyca.util.DateUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.util.Calendar

class HomeInteractorImpl(private val userDataRepository: UserDataRepository) : HomeInteractor {

    override fun getUserName(): Flow<String> = userDataRepository.getUserName()

    override suspend fun getLesson(): LessonSection {
        return LessonSection( // Доделать, после добавления запроса на получение тем и уроков
            id = 1,
            section = "Урок 1",
            title = "Фонема П1",
            text = "",
            tasksList = emptyList()
        )
    }

    override fun getDailyActivity(): Flow<DailyActivity?> {
        val startOfDay = DateUtils.getStartOfDayTimestamp()
        return userDataRepository.getTodayActivity(startOfDay)
    }

    override fun getRewards(): Flow<List<Reward>> = userDataRepository.getAllRewards()
}