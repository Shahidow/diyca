package com.example.diyca.feature.home.screens.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.diyca.domain.home.activity.ActivityInteractor
import com.example.diyca.domain.home.models.DailyActivity
import com.example.diyca.util.DATE_FORMAT
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAdjusters

class ActivityViewModel(private val activityInteractor: ActivityInteractor) : ViewModel() {
    private val _state = MutableStateFlow(ActivityState())
    val state = _state.asStateFlow()

    private val _effects = Channel<ActivityEffect>(Channel.BUFFERED)
    val effects = _effects.receiveAsFlow()

    init {
        dispatch(ActivityMsg.LoadData)
    }

    fun dispatch(msg: ActivityMsg) {
        when (msg) {
            is ActivityMsg.BackClicked -> {
                viewModelScope.launch {
                    _effects.send(ActivityEffect.NavigateBack)
                }
            }

            is ActivityMsg.ActivityCalendarClicked -> {
                viewModelScope.launch {
                    _effects.send(ActivityEffect.NavigateToActivityCalendar)
                }
            }

            is ActivityMsg.LoadData -> {
                loadWeeklyData()
            }
        }
    }

    private fun loadWeeklyData() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            updateDate()
            val mondayDate = getMondayDate()
            val rawActivities = activityInteractor.getWeeklyActivities(mondayDate.toString())
            val sortedWeek = fillWeeklyGaps(rawActivities, mondayDate)
            val todayStr = LocalDate.now().toString()
            val todayActivity = sortedWeek.find { it?.date == todayStr }
            _state.update { it.copy(
                activities = sortedWeek,
                todayActivity = todayActivity,
                isLoading = false
            ) }
        }
    }

    private fun updateDate() {
        val now = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern(DATE_FORMAT)
        _state.update { it.copy(todayDate = now.format(formatter)) }
    }

    private fun getMondayDate(): LocalDate {
        return LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
    }

    private fun fillWeeklyGaps(
        activities: List<DailyActivity>,
        monday: LocalDate
    ): List<DailyActivity?> {
        return List(7) { i ->
            val currentDay = monday.plusDays(i.toLong()).toString()
            activities.find { it.date == currentDay }
        }
    }
}