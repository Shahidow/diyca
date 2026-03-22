package com.example.diyca.feature.home.screens.activity

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
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap.Companion.Round
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.example.diyca.R
import com.example.diyca.domain.home.models.DailyActivity
import com.example.diyca.ui.coponents.CustomBoxContainer
import com.example.diyca.ui.coponents.CustomCircularProgress
import com.example.diyca.ui.navigation.ScreenRoutes
import com.example.diyca.ui.navigation.popUpToRoute
import com.example.diyca.ui.theme.Dimens
import com.example.diyca.ui.theme.PrimaryTeal
import com.example.diyca.ui.theme.TealLight
import com.example.diyca.util.DAILY_LESSONS_GOAL
import com.example.diyca.util.DAILY_TASKS_GOAL
import org.koin.androidx.compose.koinViewModel

@Composable
fun ActivityScreen(navController: NavController) {
    val viewModel: ActivityViewModel = koinViewModel()
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.effects.collect { effect ->
            when (effect) {
                ActivityEffect.NavigateBack -> navController.popUpToRoute(ScreenRoutes.HomeRout)
                ActivityEffect.NavigateToActivityCalendar -> TODO()
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(Dimens.Padding_16)
    ) {
        ActivityHeader(state.todayDate, viewModel)
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
        Icon(
            painterResource(R.drawable.ic_back),
            contentDescription = null,
            modifier = Modifier
                .padding(Dimens.Padding_8)
                .clickable { viewModel.dispatch(ActivityMsg.GoBack) },
            tint = MaterialTheme.colorScheme.primary
        )
        Text(text = "${stringResource(R.string.today)}, $todayDate")
        Icon(
            painter = painterResource(R.drawable.calendar),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.clickable { viewModel.dispatch(ActivityMsg.GoToActivityCalendar) }
        )
    }
}

@Composable
fun WeeklyActivityLine(activities: List<DailyActivity?>) {
    val days = listOf(
        R.string.mon, R.string.tue, R.string.wed,
        R.string.thu, R.string.fri, R.string.sat, R.string.sun
    )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Dimens.Padding_16),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        days.forEachIndexed { index, dayRes ->
            val dayActivity = activities.getOrNull(index)
            val lessonProgress =
                (dayActivity?.lessonsCompleted?.toFloat() ?: 0f) / DAILY_LESSONS_GOAL
            val taskProgress = (dayActivity?.tasksCompleted?.toFloat() ?: 0f) / DAILY_TASKS_GOAL
            WeeklyActivityItem(
                dayTitle = stringResource(dayRes),
                lessonProgress = lessonProgress.coerceIn(0f, 1f),
                taskProgress = taskProgress.coerceIn(0f, 1f)
            )
        }
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
        CustomCircularProgress(
            lessonProgress = lessonsDone.toFloat() / DAILY_LESSONS_GOAL,
            taskProgress = tasksDone.toFloat() / DAILY_TASKS_GOAL,
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
                Text(stringResource(R.string.lessons_completed))
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
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(Dimens.Padding_16))
        CustomBoxContainer(
            color = TealLight,
            borderColor = PrimaryTeal,
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(Dimens.Padding_16)
        ) {
            Column {
                Text(stringResource(R.string.tasks_completed))
                Spacer(modifier = Modifier.height(Dimens.Padding_8))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("$tasksDone/$DAILY_TASKS_GOAL")
                    LinearProgressIndicator(
                        progress = { (tasksDone.toFloat() / DAILY_TASKS_GOAL).coerceIn(0f, 1f) },
                        modifier = Modifier
                            .weight(1f)
                            .height(Dimens.Padding_12)
                            .padding(start = Dimens.Padding_4),
                        color = PrimaryTeal,
                        trackColor = PrimaryTeal.copy(alpha = 0.2f),
                        strokeCap = Round
                    )
                }
            }
        }
    }
}

@Composable
fun WeeklyActivityItem(
    dayTitle: String,
    lessonProgress: Float,
    taskProgress: Float
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(Dimens.Padding_8)
    ) {
        Text(
            text = dayTitle,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        CustomCircularProgress(
            lessonProgress = lessonProgress,
            taskProgress = taskProgress,
            size = Dimens.Size_32,
        )
    }
}