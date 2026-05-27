package com.example.diyca.feature.home.screens.activity

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import com.example.diyca.R
import com.example.diyca.domain.home.models.DailyActivity
import com.example.diyca.ui.coponents.CustomBackButton
import com.example.diyca.ui.coponents.CustomBoxContainer
import com.example.diyca.ui.coponents.CustomDoubleCircularProgress
import com.example.diyca.ui.coponents.CustomProgressBar
import com.example.diyca.ui.navigation.ScreenRoutes
import com.example.diyca.ui.navigation.navigateSafe
import com.example.diyca.ui.navigation.popBackStackSafe
import com.example.diyca.ui.theme.Dimens
import com.example.diyca.ui.theme.PrimaryTeal
import com.example.diyca.util.DAILY_LESSONS_GOAL
import com.example.diyca.util.DAILY_TASKS_GOAL
import org.koin.androidx.compose.koinViewModel
import java.time.LocalDate

@Composable
fun ActivityScreen(navController: NavController) {
    val viewModel: ActivityViewModel = koinViewModel()
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.effects.collect { effect ->
            when (effect) {
                is ActivityEffect.NavigateBack -> navController.popBackStackSafe()
                is ActivityEffect.NavigateToActivityCalendar -> navController.navigateSafe(ScreenRoutes.ActivityCalendarRout)
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
            .padding(Dimens.Padding_16)
    ) {
        ActivityHeader(state.todayDate, viewModel)
        Spacer(modifier = Modifier.height(Dimens.Padding_16))
        WeeklyActivityLine(state.activities)
        Spacer(modifier = Modifier.height(Dimens.Padding_36))
        TodayActivity(state.todayActivity)
    }
}

@Composable
fun ActivityHeader(todayDate: String, viewModel: ActivityViewModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        CustomBackButton(onClick = { viewModel.dispatch(ActivityMsg.BackClicked) })
        Text(
            text = "${stringResource(R.string.today)}, $todayDate",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground
        )
        Icon(
            painter = painterResource(R.drawable.ic_calendar),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.clickable { viewModel.dispatch(ActivityMsg.ActivityCalendarClicked) }
        )
    }
}

@Composable
fun WeeklyActivityLine(activities: List<DailyActivity?>) {
    val days = listOf(
        R.string.mon, R.string.tue, R.string.wed,
        R.string.thu, R.string.fri, R.string.sat, R.string.sun
    )
    val todayIndex = LocalDate.now().dayOfWeek.value - 1
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Dimens.Padding_4),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        days.forEachIndexed { index, dayRes ->
            val dayActivity = activities.getOrNull(index)
            val lessonProgress = dayActivity?.lessonsCompleted ?: 0
            val taskProgress = dayActivity?.tasksCompleted ?: 0
            WeeklyActivityItem(
                dayTitle = stringResource(dayRes),
                lessonProgress = lessonProgress,
                taskProgress = taskProgress,
                isToday = index == todayIndex
            )
        }
    }
}

@Composable
fun WeeklyActivityItem(
    dayTitle: String,
    lessonProgress: Int,
    taskProgress: Int,
    isToday: Boolean = false
) {
    Column(
        modifier = Modifier.padding(horizontal = Dimens.Padding_4, vertical = Dimens.Padding_8),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(Dimens.Padding_8)
    ) {
        Text(
            text = dayTitle,
            style = MaterialTheme.typography.labelMedium,
            color = if (isToday) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground,
            fontWeight = if (isToday) FontWeight.Bold else FontWeight.Normal
        )
        CustomDoubleCircularProgress(
            lessonsCompleted = lessonProgress,
            tasksCompleted = taskProgress,
            isAnimationEnabled = false,
            size = Dimens.Size_32,
        )
    }
}

@Composable
fun TodayActivity(dayActivity: DailyActivity?) {
    val lessonsDone = dayActivity?.lessonsCompleted ?: 0
    val tasksDone = dayActivity?.tasksCompleted ?: 0
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        CustomDoubleCircularProgress(
            lessonsCompleted = lessonsDone,
            tasksCompleted = tasksDone,
            size = Dimens.Size_232,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(Dimens.Padding_36))
        CustomBoxContainer(
            color = MaterialTheme.colorScheme.secondary,
            borderColor = MaterialTheme.colorScheme.outline,
            modifier = Modifier,
            contentPadding = PaddingValues(Dimens.Padding_16)
        ) {
            Column {
                Text(
                    stringResource(R.string.lessons_completed),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.height(Dimens.Padding_8))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(Dimens.Padding_4)
                ) {
                    repeat(DAILY_LESSONS_GOAL) { index ->
                        val isCompleted = index < lessonsDone
                        Icon(
                            painter = painterResource(
                                if (isCompleted) R.drawable.ic_done
                                else R.drawable.ic_todo
                            ),
                            contentDescription = null,
                            tint = if (isCompleted) MaterialTheme.colorScheme.primary
                            else MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(Dimens.Padding_16))
        CustomBoxContainer(
            color = PrimaryTeal.copy(alpha = 0.05f),
            borderColor = PrimaryTeal,
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(Dimens.Padding_16)
        ) {
            Column {
                Text(
                    text = stringResource(R.string.tasks_completed),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.height(Dimens.Padding_8))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "$tasksDone/$DAILY_TASKS_GOAL",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    CustomProgressBar(
                        progress = (tasksDone.toFloat() / DAILY_TASKS_GOAL).coerceIn(0f, 1f),
                        color = PrimaryTeal,
                    )
                }
            }
        }
    }
}