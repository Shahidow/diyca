package com.example.diyca.feature.home.screens.mein

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavHostController
import com.example.diyca.R
import com.example.diyca.domain.home.models.DailyActivity
import com.example.diyca.domain.home.models.Reward
import com.example.diyca.domain.home.settings.models.UserAvatar
import com.example.diyca.domain.learning.models.Lesson
import com.example.diyca.ui.coponents.CustomBoxContainer
import com.example.diyca.ui.coponents.CustomDialog
import com.example.diyca.ui.coponents.CustomDoubleCircularProgress
import com.example.diyca.ui.coponents.CustomErrorBox
import com.example.diyca.ui.coponents.CustomRewardBox
import com.example.diyca.ui.coponents.shimmerBrush
import com.example.diyca.ui.navigation.ScreenRoutes
import com.example.diyca.ui.navigation.navigateSafe
import com.example.diyca.ui.theme.Dimens
import com.example.diyca.ui.theme.PrimaryTeal
import com.example.diyca.util.ErrorType
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(navHostController: NavHostController) {
    val viewModel: HomeViewModel = koinViewModel()
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current
    val avatar: UserAvatar? = state.avatar
    val pullToRefreshState = rememberPullToRefreshState()

    BackHandler(enabled = true) { viewModel.dispatch(HomeMsg.BackClicked) }

    LaunchedEffect(Unit) {
        viewModel.effects.collect { effect ->
            when (effect) {
                is HomeEffect.CloseApp -> (context as? Activity)?.finishAffinity()
                is HomeEffect.GoToProfile -> navHostController.navigateSafe(ScreenRoutes.ProfileRout)
                is HomeEffect.GoToActivity -> navHostController.navigateSafe(ScreenRoutes.ActivityRout)
                is HomeEffect.StartLesson -> {
                    val lesson = state.todayLesson
                    lesson ?: return@collect
                    navHostController.navigateSafe(
                        ScreenRoutes.LessonRout(
                            id = lesson.id,
                            topicId = state.todayLessonTopicId,
                            number = lesson.number,
                            title = lesson.title,
                            text = lesson.text,
                            image = lesson.image,
                            audio = lesson.audio,
                            tasksCount = lesson.tasksCount
                        )
                    )
                }
            }
        }
    }

    PullToRefreshBox(
        isRefreshing = state.isRefreshing,
        onRefresh = { viewModel.dispatch(HomeMsg.RetryLessonLoad) },
        modifier = Modifier.fillMaxSize(),
        state = pullToRefreshState,
        indicator = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.TopCenter
            ) {
                PullToRefreshDefaults.Indicator(
                    state = pullToRefreshState,
                    isRefreshing = state.isRefreshing,
                    containerColor = MaterialTheme.colorScheme.surface,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.secondary)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = Dimens.Padding_16)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = Dimens.Padding_16),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(Dimens.Size_56)
                        .clip(CircleShape)
                        .then(
                            if (avatar == null) {
                                Modifier.background(shimmerBrush(showShimmer = true))
                            } else {
                                Modifier.border(
                                    width = Dimens.Size_2,
                                    color = MaterialTheme.colorScheme.primary,
                                    shape = CircleShape
                                )
                            }
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    if (avatar != null) {
                        Icon(
                            painter = painterResource(avatar.resId),
                            contentDescription = null,
                            tint = Color.Unspecified,
                            modifier = Modifier
                                .fillMaxSize()
                                .clickable { viewModel.dispatch(HomeMsg.GoToProfile) }
                        )
                    }
                }
                Text(
                    text = stringResource(R.string.hallo_user, state.userName),
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(start = Dimens.Padding_16)
                )
            }
            Spacer(modifier = Modifier.height(Dimens.Padding_16))

            HomeTodayLesson(
                state.isLoading,
                state.todayLesson,
                state.error,
                state.isCourseFinished,
                onClick = { viewModel.dispatch(HomeMsg.StartLesson) },
                onRetry = { viewModel.dispatch(HomeMsg.RetryLessonLoad) })
            Spacer(modifier = Modifier.height(Dimens.Padding_16))

            Text(
                stringResource(R.string.activity_today),
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(start = Dimens.Padding_16)
            )
            Spacer(modifier = Modifier.height(Dimens.Padding_8))

            HomeTodayActivity(
                dailyActivity = state.dailyActivity,
                viewModel = viewModel
            )
            Spacer(modifier = Modifier.height(Dimens.Padding_16))

            Text(
                stringResource(R.string.awards),
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(start = Dimens.Padding_16)
            )
            Spacer(modifier = Modifier.height(Dimens.Padding_8))

            HomeRewards(state.rewards)
            Spacer(modifier = Modifier.height(Dimens.Padding_16))
        }

        if (state.showConfirmation) {
            CustomDialog(
                title = stringResource(R.string.exit),
                message = stringResource(R.string.exit_confirmation),
                confirmButtonText = stringResource(R.string.action_yes),
                dismissButtonText = stringResource(R.string.action_cancel),
                onConfirm = { viewModel.dispatch(HomeMsg.ConfirmExit) },
                onDismiss = { viewModel.dispatch(HomeMsg.DismissExitDialog) }
            )
        }
    }
}

@Composable
fun HomeTodayLesson(
    isLoading: Boolean,
    lesson: Lesson?,
    error: ErrorType?,
    isCourseFinished: Boolean,
    onClick: () -> Unit,
    onRetry: () -> Unit
) {
    Box(
        modifier = Modifier
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.primary,
                        MaterialTheme.colorScheme.tertiary
                    )
                ),
                shape = RoundedCornerShape(Dimens.RoundedCorner_16)
            )
            .fillMaxWidth()
            .heightIn(min = Dimens.Size_150)
            .padding(Dimens.Padding_16),
        contentAlignment = Alignment.Center
    ) {
        when {
            isLoading -> {
                val brush = shimmerBrush()
                Column(modifier = Modifier.fillMaxWidth()) {
                    Box(
                        modifier = Modifier
                            .size(width = Dimens.Size_200, height = Dimens.Size_24)
                            .background(brush)
                    )
                    Spacer(modifier = Modifier.height(Dimens.Padding_8))
                    Box(
                        modifier = Modifier
                            .size(width = Dimens.Size_150, height = Dimens.Size_16)
                            .background(brush)
                    )
                    Spacer(modifier = Modifier.height(Dimens.Padding_16))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(Dimens.Padding_56)
                            .clip(RoundedCornerShape(Dimens.RoundedCorner_12))
                            .background(brush)
                    )
                }
            }

            error != null -> {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    CustomErrorBox(
                        onClick = onRetry,
                        errorType = error,
                        imageSize = Dimens.Size_56,
                        modifier = Modifier.fillMaxWidth(),
                        isButtonEnabled = false
                    )
                    Spacer(modifier = Modifier.height(Dimens.Padding_8))
                    Text(
                        text = stringResource(R.string.pull_to_refresh),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f)
                    )
                }
            }

            isCourseFinished -> {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(R.drawable.ic_cup),
                        contentDescription = null,
                        modifier = Modifier.size(Dimens.Size_56)
                    )
                    Spacer(modifier = Modifier.height(Dimens.Padding_16))
                    Text(
                        text = stringResource(R.string.course_completed),
                        color = MaterialTheme.colorScheme.onPrimary,
                        style = MaterialTheme.typography.headlineLarge,
                    )
                }
            }

            lesson != null -> {
                HomeTodayLessonItem(lesson.number, lesson.title, onClick)
            }
        }
    }
}

@Composable
fun HomeTodayLessonItem(lessonNumber: Int, title: String, onClick: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = stringResource(R.string.your_task_for_today),
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.headlineLarge,
        )
        Spacer(modifier = Modifier.height(Dimens.Padding_8))
        Text(
            text = stringResource(R.string.do_theory_and_practice),
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(Dimens.Padding_8))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.2f),
                    shape = RoundedCornerShape(Dimens.RoundedCorner_12)
                )
                .clickable { onClick() }
                .padding(Dimens.Padding_8),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(Dimens.Size_32)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.onPrimary),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_arrow_right),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }
            Spacer(modifier = Modifier.width(Dimens.Padding_16))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = stringResource(R.string.lesson_number, lessonNumber),
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onPrimary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
fun HomeTodayActivity(dailyActivity: DailyActivity?, viewModel: HomeViewModel) {
    CustomBoxContainer(
        onClick = { viewModel.dispatch(HomeMsg.GoToActivity) },
        modifier = Modifier,
        color = MaterialTheme.colorScheme.background,
        borderColor = Color.Transparent
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = Dimens.Padding_24),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            CustomDoubleCircularProgress(
                lessonsCompleted = dailyActivity?.lessonsCompleted ?: 0,
                tasksCompleted = dailyActivity?.tasksCompleted ?: 0
            )
            Column {
                HomeActivityItem(
                    MaterialTheme.colorScheme.primary,
                    stringResource(R.string.lessons_completed)
                )
                Spacer(modifier = Modifier.height(Dimens.Padding_16))
                HomeActivityItem(PrimaryTeal, stringResource(R.string.tasks_completed))
            }

        }
    }
}

@Composable
fun HomeActivityItem(
    color: Color,
    text: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .height(Dimens.Size_10)
                .width(Dimens.Size_16)
                .border(
                    width = Dimens.Size_5,
                    color = color,
                    shape = RoundedCornerShape(Dimens.RoundedCorner_5)
                )
        )
        Text(
            text = text,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(start = Dimens.Padding_8)
        )
    }
}

@Composable
fun HomeRewards(rewards: List<Reward>) {
    CustomBoxContainer(
        contentPadding = PaddingValues(Dimens.Padding_16),
        color = MaterialTheme.colorScheme.background,
        borderColor = Color.Transparent
    ) {
        if (rewards.isEmpty()) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_award),
                    tint = MaterialTheme.colorScheme.primary,
                    contentDescription = null,
                )
                Spacer(modifier = Modifier.height(Dimens.Padding_4))
                Text(
                    text = stringResource(R.string.no_rewards),
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.labelLarge,
                )
            }

        } else {
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top,
                contentPadding = PaddingValues(horizontal = Dimens.Padding_8),
                horizontalArrangement = Arrangement.spacedBy(Dimens.Padding_8)
            ) {
                items(rewards.size) { index ->
                    CustomRewardBox(rewards[index])
                }
            }
        }
    }
}