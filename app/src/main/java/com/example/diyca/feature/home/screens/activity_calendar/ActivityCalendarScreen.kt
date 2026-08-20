package com.example.diyca.feature.home.screens.activity_calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.example.diyca.R
import com.example.diyca.domain.home.models.DailyActivity
import com.example.diyca.ui.coponents.CustomBackButton
import com.example.diyca.ui.coponents.CustomDoubleCircularProgress
import com.example.diyca.ui.navigation.popBackStackSafe
import com.example.diyca.ui.theme.Dimens
import org.koin.androidx.compose.koinViewModel
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

@Suppress("DEPRECATION")
@Composable
fun ActivityCalendarScreen(navController: NavController) {
    val viewModel: ActivityCalendarViewModel = koinViewModel()
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.effects.collect { effect ->
            when (effect) {
                is ActivityCalendarEffect.GoBack -> navController.popBackStackSafe()
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
            .padding(Dimens.Padding_16),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ActivityCalendarHeader(state.todayDateString, viewModel)
        Spacer(modifier = Modifier.height(Dimens.Padding_16))
        WeeklyActivityCalendarLine()
        Spacer(modifier = Modifier.height(Dimens.Padding_8))

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = Dimens.Padding_24)
        ) {
            state.groupedMonths.forEach { (year, months) ->
                stickyHeader {
                    Text(
                        text = year.toString(),
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.background)
                            .padding(vertical = Dimens.Padding_8),
                        textAlign = TextAlign.Center,
                    )
                }
                items(months) { month ->
                    val configuration = LocalConfiguration.current
                    val currentLocale = configuration.locales[0]
                    Text(
                        text = month.month.getDisplayName(
                            TextStyle.FULL_STANDALONE,
                            Locale("ru")
                        )
                            .replaceFirstChar { it.titlecase(currentLocale) },
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = Dimens.Padding_8),
                        textAlign = TextAlign.Center,
                    )
                    MonthGrid(month, state.activityMap, state.today)
                    Spacer(modifier = Modifier.height(Dimens.Padding_16))
                }
            }

            item {
                Text(
                    text = (stringResource(R.string.activity_start_point)),
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = Dimens.Padding_24),
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}

@Composable
fun ActivityCalendarHeader(todayDate: String, viewModel: ActivityCalendarViewModel) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        val (icon, title) = createRefs()
        CustomBackButton(
            onClick = { viewModel.dispatch(ActivityCalendarMsg.BackClicked) },
            modifier = Modifier.constrainAs(icon) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
            }
        )
        Text(
            text = "${stringResource(R.string.today)}, $todayDate",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.constrainAs(title) {
                top.linkTo(icon.top)
                bottom.linkTo(icon.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )
    }
}

@Composable
fun WeeklyActivityCalendarLine() {
    val days = listOf(
        R.string.mon, R.string.tue, R.string.wed,
        R.string.thu, R.string.fri, R.string.sat, R.string.sun
    )
    val todayIndex = LocalDate.now().dayOfWeek.value - 1
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = Dimens.Padding_8),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        days.forEachIndexed { index, dayRes ->
            val isToday = index == todayIndex
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(dayRes),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.labelMedium,
                    color = if (isToday) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground,
                    fontWeight = if (isToday) FontWeight.Bold else FontWeight.Normal
                )
            }
        }
    }
}

@Composable
fun MonthGrid(month: YearMonth, activityMap: Map<LocalDate, DailyActivity>, today: LocalDate) {
    val firstDayOfMonth = month.atDay(1)
    val daysInMonth = month.lengthOfMonth()
    val firstDayOffset = firstDayOfMonth.dayOfWeek.value - 1

    val totalSlots = daysInMonth + firstDayOffset
    val rows = (totalSlots + 6) / 7

    Column(verticalArrangement = Arrangement.spacedBy(Dimens.Padding_8)) {
        for (row in 0 until rows) {
            Row(modifier = Modifier.fillMaxWidth()) {
                for (column in 0 until 7) {
                    val dayIndex = row * 7 + column
                    val dayNumber = dayIndex - firstDayOffset + 1

                    Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                        if (dayNumber in 1..daysInMonth) {
                            val date = month.atDay(dayNumber)
                            val activity = activityMap[date]
                            val isToday = date == today

                            DailyActivityCalendarItem(
                                day = dayNumber.toString(),
                                lessonProgress = (activity?.lessonsCompleted ?: 0),
                                taskProgress = (activity?.tasksCompleted ?: 0),
                                isToday = isToday
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DailyActivityCalendarItem(
    day: String,
    lessonProgress: Int,
    taskProgress: Int,
    isToday: Boolean
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(24.dp)
                .background(
                    color = if (isToday) MaterialTheme.colorScheme.primary else Color.Transparent,
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = day,
                color = if (isToday) MaterialTheme.colorScheme.surface else MaterialTheme.colorScheme.onSurface,
                fontWeight = if (isToday) FontWeight.Bold else FontWeight.Normal
            )
        }
        Spacer(modifier = Modifier.height(Dimens.Padding_4))
        CustomDoubleCircularProgress(
            lessonsCompleted = lessonProgress,
            tasksCompleted = taskProgress,
            isAnimationEnabled = false,
            size = Dimens.Size_32
        )
    }
}

