package com.example.diyca.feature.home.screens.activity_calendar

import com.example.diyca.domain.home.models.DailyActivity
import com.example.diyca.feature.home.screens.activity_calendar.models.MonthDisplayModel
import java.time.LocalDate

data class ActivityCalendarState(
    val isLoading: Boolean = false,
    val todayDateString: String = "",
    val today: LocalDate = LocalDate.now(),
    val groupedMonths: List<MonthDisplayModel> = emptyList(),
    val activities: List<DailyActivity> = emptyList(),
    val activityMap: Map<LocalDate, DailyActivity> = emptyMap(),
)
