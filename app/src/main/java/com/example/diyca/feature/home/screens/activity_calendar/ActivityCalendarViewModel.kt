package com.example.diyca.feature.home.screens.activity_calendar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.diyca.domain.home.activity.ActivityInteractor
import com.example.diyca.domain.home.models.DailyActivity
import com.example.diyca.feature.home.screens.activity_calendar.models.MonthDisplayModel
import com.example.diyca.util.DATE_FORMAT
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.Locale

class ActivityCalendarViewModel(private val activityInteractor: ActivityInteractor) : ViewModel() {
    private val _state = MutableStateFlow(ActivityCalendarState())
    val state = _state.asStateFlow()

    private val _effects = Channel<ActivityCalendarEffect>(Channel.BUFFERED)
    val effects = _effects.receiveAsFlow()

    init {
        dispatch(ActivityCalendarMsg.LoadData)
    }

    fun dispatch(msg: ActivityCalendarMsg) {
        when (msg) {
            is ActivityCalendarMsg.LoadData -> {
                _state.update { it.copy(isLoading = true) }
                viewModelScope.launch {
                    updateDate()
                    val activities = activityInteractor.getActivities()
                    _state.update { it ->
                        it.copy(
                            activities = activities,
                            activityMap = activities.associateBy { LocalDate.parse(it.date) },
                            groupedMonths = calculateDisplayMonths(activities),
                            isLoading = false
                        )
                    }
                }
            }

            is ActivityCalendarMsg.BackClicked -> {
                viewModelScope.launch {
                    _effects.send(ActivityCalendarEffect.GoBack)
                }
            }
        }
    }

    private fun updateDate() {
        val now = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern(
            DATE_FORMAT,
            Locale("ru")
        )
        val dateString = now.format(formatter)
        _state.update { it.copy(todayDateString = dateString, today = now) }
    }

    private fun calculateDisplayMonths(activities: List<DailyActivity>): List<MonthDisplayModel> {
        val currentMonth = YearMonth.now()
        val firstDate = if (activities.isEmpty()) {
            currentMonth.atDay(1)
        } else {
            activities.mapNotNull {
                runCatching { LocalDate.parse(it.date) }.getOrNull()
            }.minOrNull() ?: currentMonth.atDay(1)
        }
        val months = mutableListOf<YearMonth>()
        var temp = YearMonth.from(firstDate)
        while (!temp.isAfter(currentMonth)) {
            months.add(temp)
            temp = temp.plusMonths(1)
        }
        return months.sortedDescending()
            .groupBy { it.year }
            .map { (year, months) -> MonthDisplayModel(year, months) }
    }
}