package com.example.diyca.feature.home.screens.activity

import android.icu.util.Calendar
import java.util.Locale
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.diyca.domain.home.activity.ActivityInteractor
import com.example.diyca.domain.home.models.DailyActivity
import com.example.diyca.util.DateUtils
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

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
            is ActivityMsg.GoBack -> {
                viewModelScope.launch {
                    _effects.send(ActivityEffect.NavigateBack)
                }
            }

            is ActivityMsg.GoToActivityCalendar -> {
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
            val mondayTimestamp = getMondayTimestamp()
            val rawActivities = activityInteractor.getWeeklyActivities(mondayTimestamp)
            val sortedWeek = fillWeeklyGaps(rawActivities, mondayTimestamp)
            val todayStart = DateUtils.getStartOfDayTimestamp()
            val todayActivity = sortedWeek.find { it?.date == todayStart }
            _state.update { it.copy(
                activities = sortedWeek,
                todayActivity = todayActivity,
                isLoading = false
            ) }
        }
    }

    private fun updateDate() {
        val now = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern(
            "dd MMM yyyy",
            Locale.getDefault()
        )
        val dateString = now.format(formatter)
        _state.update { it.copy(todayDate = dateString) }
    }

    private fun getMondayTimestamp(): Long {
        val calendar = Calendar.getInstance()
        calendar.firstDayOfWeek = Calendar.MONDAY
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
        return DateUtils.getStartOfDayFromTimestamp(calendar.timeInMillis)
    }

    private fun fillWeeklyGaps(
        activities: List<DailyActivity>,
        mondayTimestamp: Long
    ): List<DailyActivity?> {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = mondayTimestamp
        return List(7) { _ ->
            val currentDayStart = DateUtils.getStartOfDayFromTimestamp(calendar.timeInMillis)
            val activityForDay = activities.find {
                DateUtils.getStartOfDayFromTimestamp(it.date) == currentDayStart
            }
            calendar.add(Calendar.DAY_OF_YEAR, 1)
            activityForDay
        }
    }
}